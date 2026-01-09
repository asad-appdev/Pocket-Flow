package com.xasdify.pocketflow.loans.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class AddLoanFormState(
    val type: LoanType = LoanType.TAKEN,
    val lenderName: String = "",
    val principalAmount: String = "",
    val interestRate: String = "0",
    val currencyCode: String = "USD",
    val startDate: Long = getCurrentTimeMilli(),
    val dueDate: Long? = null,
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val validationErrors: Map<String, String> = emptyMap(),
    val isSaved: Boolean = false
)

class AddLoanViewModel(
    private val loanRepository: LoanRepository,
    private val editLoanId: Long? = null
) : ViewModel() {

    private val _formState = MutableStateFlow(AddLoanFormState())
    val formState: StateFlow<AddLoanFormState> = _formState.asStateFlow()

    init {
        editLoanId?.let { loadLoan(it) }
    }

    private fun loadLoan(loanId: Long) {
        viewModelScope.launch {
            try {
                _formState.value = _formState.value.copy(isLoading = true)
                
                val loan = loanRepository.getLoanById(loanId)
                if (loan != null) {
                    _formState.value = AddLoanFormState(
                        type = LoanType.valueOf(loan.type),
                        lenderName = loan.lenderName,
                        principalAmount = loan.principalAmount.toString(),
                        interestRate = loan.interestRate.toString(),
                        currencyCode = loan.currencyCode,
                        startDate = loan.startDate,
                        dueDate = loan.dueDate,
                        description = loan.description ?: "",
                        isLoading = false
                    )
                } else {
                    _formState.value = _formState.value.copy(
                        isLoading = false,
                        error = "Loan not found"
                    )
                }
            } catch (e: Exception) {
                _formState.value = _formState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load loan"
                )
            }
        }
    }

    fun updateType(type: LoanType) {
        _formState.value = _formState.value.copy(type = type)
    }

    fun updateLenderName(name: String) {
        _formState.value = _formState.value.copy(
            lenderName = name,
            validationErrors = _formState.value.validationErrors - "lenderName"
        )
    }

    fun updatePrincipalAmount(amount: String) {
        _formState.value = _formState.value.copy(
            principalAmount = amount,
            validationErrors = _formState.value.validationErrors - "principalAmount"
        )
    }

    fun updateInterestRate(rate: String) {
        _formState.value = _formState.value.copy(
            interestRate = rate,
            validationErrors = _formState.value.validationErrors - "interestRate"
        )
    }

    fun updateCurrencyCode(currency: String) {
        _formState.value = _formState.value.copy(currencyCode = currency)
    }

    fun updateStartDate(date: Long) {
        _formState.value = _formState.value.copy(startDate = date)
    }

    fun updateDueDate(date: Long?) {
        _formState.value = _formState.value.copy(dueDate = date)
    }

    fun updateDescription(description: String) {
        _formState.value = _formState.value.copy(description = description)
    }

    fun saveLoan() {
        val formState = _formState.value
        val validationErrors = validateForm(formState)
        
        if (validationErrors.isNotEmpty()) {
            _formState.value = formState.copy(validationErrors = validationErrors)
            return
        }

        viewModelScope.launch {
            try {
                _formState.value = formState.copy(isLoading = true)
                
                val currentTime = getCurrentTimeMilli()
                val loan = Loan(
                    id = editLoanId ?: 0,
                    type = formState.type.name,
                    lenderName = formState.lenderName.trim(),
                    principalAmount = formState.principalAmount.toDouble(),
                    interestRate = formState.interestRate.toDoubleOrNull() ?: 0.0,
                    currencyCode = formState.currencyCode,
                    startDate = formState.startDate,
                    dueDate = formState.dueDate,
                    status = LoanStatus.ACTIVE.name,
                    description = formState.description.trim().ifBlank { null },
                    totalPaid = 0.0,
                    remainingBalance = formState.principalAmount.toDouble(),
                    createdAt = currentTime,
                    updatedAt = currentTime
                )

                if (editLoanId != null) {
                    loanRepository.updateLoan(loan)
                } else {
                    loanRepository.insertLoan(loan)
                }
                
                _formState.value = formState.copy(
                    isLoading = false,
                    isSaved = true
                )
            } catch (e: Exception) {
                _formState.value = formState.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to save loan"
                )
            }
        }
    }

    private fun validateForm(formState: AddLoanFormState): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        
        if (formState.lenderName.isBlank()) {
            errors["lenderName"] = if (formState.type == LoanType.TAKEN) {
                "Please enter the lender's name"
            } else {
                "Please enter the borrower's name"
            }
        }
        
        val principalAmount = formState.principalAmount.toDoubleOrNull()
        if (principalAmount == null || principalAmount <= 0) {
            errors["principalAmount"] = "Please enter a valid amount"
        }
        
        val interestRate = formState.interestRate.toDoubleOrNull()
        if (interestRate == null || interestRate < 0) {
            errors["interestRate"] = "Please enter a valid interest rate"
        } else if (interestRate > 100) {
            errors["interestRate"] = "Interest rate cannot exceed 100%"
        }
        
        if (formState.dueDate != null && formState.dueDate < formState.startDate) {
            errors["dueDate"] = "Due date cannot be before start date"
        }
        
        return errors
    }

    fun clearError() {
        _formState.value = _formState.value.copy(error = null)
    }

    fun resetForm() {
        _formState.value = AddLoanFormState()
    }
}
