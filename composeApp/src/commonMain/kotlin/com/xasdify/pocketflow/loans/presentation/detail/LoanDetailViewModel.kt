package com.xasdify.pocketflow.loans.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.loans.domain.model.LoanPayment
import com.xasdify.pocketflow.loans.domain.model.LoanWithPayments
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class LoanDetailUiState(
    val loanWithPayments: LoanWithPayments? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddPaymentDialog: Boolean = false,
    val isProcessingPayment: Boolean = false
)

data class PaymentFormState(
    val amount: String = "",
    val principalPortion: String = "",
    val interestPortion: String = "",
    val paymentDate: Long = getCurrentTimeMilli(),
    val notes: String = "",
    val validationErrors: Map<String, String> = emptyMap()
)

class LoanDetailViewModel(
    private val loanRepository: LoanRepository,
    private val loanId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoanDetailUiState(isLoading = true))
    val uiState: StateFlow<LoanDetailUiState> = _uiState.asStateFlow()

    private val _paymentFormState = MutableStateFlow(PaymentFormState())
    val paymentFormState: StateFlow<PaymentFormState> = _paymentFormState.asStateFlow()

    init {
        loadLoanDetails()
    }

    private fun loadLoanDetails() {
        viewModelScope.launch {
            try {
                loanRepository.getLoanWithPayments(loanId)
                    .catch { e ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to load loan details"
                        )
                    }
                    .collect { loanWithPayments ->
                        _uiState.value = _uiState.value.copy(
                            loanWithPayments = loanWithPayments,
                            isLoading = false,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load loan details"
                )
            }
        }
    }

    fun showAddPaymentDialog() {
        _uiState.value = _uiState.value.copy(showAddPaymentDialog = true)
        resetPaymentForm()
    }

    fun hideAddPaymentDialog() {
        _uiState.value = _uiState.value.copy(showAddPaymentDialog = false)
        resetPaymentForm()
    }

    fun updatePaymentAmount(amount: String) {
        _paymentFormState.value = _paymentFormState.value.copy(
            amount = amount,
            validationErrors = _paymentFormState.value.validationErrors - "amount"
        )
        
        // Auto-calculate portions if amount is valid
        amount.toDoubleOrNull()?.let { totalAmount ->
            val loan = _uiState.value.loanWithPayments?.loan
            if (loan != null && loan.interestRate > 0) {
                // Simple interest calculation
                val remainingBalance = loan.remainingBalance
                val interestAmount = (remainingBalance * loan.interestRate / 100 / 12).coerceAtMost(totalAmount)
                val principalAmount = totalAmount - interestAmount
                
                _paymentFormState.value = _paymentFormState.value.copy(
                    principalPortion = principalAmount.toString(),
                    interestPortion = interestAmount.toString()
                )
            } else {
                // No interest, all goes to principal
                _paymentFormState.value = _paymentFormState.value.copy(
                    principalPortion = totalAmount.toString(),
                    interestPortion = "0"
                )
            }
        }
    }

    fun updatePrincipalPortion(principal: String) {
        _paymentFormState.value = _paymentFormState.value.copy(
            principalPortion = principal,
            validationErrors = _paymentFormState.value.validationErrors - "principalPortion"
        )
    }

    fun updateInterestPortion(interest: String) {
        _paymentFormState.value = _paymentFormState.value.copy(
            interestPortion = interest,
            validationErrors = _paymentFormState.value.validationErrors - "interestPortion"
        )
    }

    fun updatePaymentDate(date: Long) {
        _paymentFormState.value = _paymentFormState.value.copy(paymentDate = date)
    }

    fun updateNotes(notes: String) {
        _paymentFormState.value = _paymentFormState.value.copy(notes = notes)
    }

    fun addPayment() {
        val formState = _paymentFormState.value
        val validationErrors = validatePaymentForm(formState)
        
        if (validationErrors.isNotEmpty()) {
            _paymentFormState.value = formState.copy(validationErrors = validationErrors)
            return
        }

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isProcessingPayment = true)
                
                val payment = LoanPayment(
                    loanId = loanId,
                    amount = formState.amount.toDouble(),
                    principalPortion = formState.principalPortion.toDouble(),
                    interestPortion = formState.interestPortion.toDouble(),
                    paymentDate = formState.paymentDate,
                    notes = formState.notes.ifBlank { null },
                    createdAt = getCurrentTimeMilli()
                )
                
                loanRepository.addPayment(payment)
                
                _uiState.value = _uiState.value.copy(
                    showAddPaymentDialog = false,
                    isProcessingPayment = false
                )
                resetPaymentForm()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to add payment",
                    isProcessingPayment = false
                )
            }
        }
    }

    private fun validatePaymentForm(formState: PaymentFormState): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        
        val amount = formState.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            errors["amount"] = "Please enter a valid amount"
        }
        
        val principal = formState.principalPortion.toDoubleOrNull()
        if (principal == null || principal < 0) {
            errors["principalPortion"] = "Please enter a valid principal amount"
        }
        
        val interest = formState.interestPortion.toDoubleOrNull()
        if (interest == null || interest < 0) {
            errors["interestPortion"] = "Please enter a valid interest amount"
        }
        
        // Check if portions sum to total amount
        if (amount != null && principal != null && interest != null) {
            if ((principal + interest - amount).let { kotlin.math.abs(it) > 0.01 }) {
                errors["amount"] = "Principal + Interest must equal total amount"
            }
        }
        
        return errors
    }

    private fun resetPaymentForm() {
        _paymentFormState.value = PaymentFormState()
    }

    fun deletePayment(paymentId: Long) {
        viewModelScope.launch {
            try {
                loanRepository.deletePayment(paymentId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete payment"
                )
            }
        }
    }

    fun closeLoan() {
        viewModelScope.launch {
            try {
                loanRepository.closeLoan(loanId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to close loan"
                )
            }
        }
    }

    fun reopenLoan() {
        viewModelScope.launch {
            try {
                loanRepository.reopenLoan(loanId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to reopen loan"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
