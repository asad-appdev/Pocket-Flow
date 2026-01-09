package com.xasdify.pocketflow.loans.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class LoansListUiState(
    val loans: List<Loan> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedType: LoanType? = null,
    val selectedStatus: LoanStatus? = null,
    val totalActiveLoans: Int = 0,
    val totalGivenAmount: Double = 0.0,
    val totalTakenAmount: Double = 0.0,
    val totalGivenRemaining: Double = 0.0,
    val totalTakenRemaining: Double = 0.0
)

class LoansListViewModel(
    private val loanRepository: LoanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoansListUiState(isLoading = true))
    val uiState: StateFlow<LoansListUiState> = _uiState.asStateFlow()

    private val _selectedType = MutableStateFlow<LoanType?>(null)
    private val _selectedStatus = MutableStateFlow<LoanStatus?>(null)

    init {
        loadLoans()
    }

    private fun loadLoans() {
        viewModelScope.launch {
            try {
                combine(
                    loanRepository.getAllLoans(),
                    _selectedType,
                    _selectedStatus
                ) { allLoans, type, status ->
                    var filteredLoans = allLoans
                    
                    // Apply type filter
                    type?.let {
                        filteredLoans = filteredLoans.filter { loan -> loan.type == it.name }
                    }
                    
                    // Apply status filter
                    status?.let {
                        filteredLoans = filteredLoans.filter { loan -> loan.status == it.name }
                    }
                    
                    // Calculate statistics
                    val stats = calculateStatistics(allLoans)
                    
                    LoansListUiState(
                        loans = filteredLoans,
                        isLoading = false,
                        selectedType = type,
                        selectedStatus = status,
                        totalActiveLoans = stats.totalActiveLoans,
                        totalGivenAmount = stats.totalGivenAmount,
                        totalTakenAmount = stats.totalTakenAmount,
                        totalGivenRemaining = stats.totalGivenRemaining,
                        totalTakenRemaining = stats.totalTakenRemaining
                    )
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load loans"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load loans"
                )
            }
        }
    }

    private data class LoanStatistics(
        val totalActiveLoans: Int,
        val totalGivenAmount: Double,
        val totalTakenAmount: Double,
        val totalGivenRemaining: Double,
        val totalTakenRemaining: Double
    )

    private fun calculateStatistics(loans: List<Loan>): LoanStatistics {
        val activeLoans = loans.filter { it.status == LoanStatus.ACTIVE.name }
        
        return LoanStatistics(
            totalActiveLoans = activeLoans.size,
            totalGivenAmount = loans
                .filter { it.type == LoanType.GIVEN.name }
                .sumOf { it.principalAmount },
            totalTakenAmount = loans
                .filter { it.type == LoanType.TAKEN.name }
                .sumOf { it.principalAmount },
            totalGivenRemaining = activeLoans
                .filter { it.type == LoanType.GIVEN.name }
                .sumOf { it.remainingBalance },
            totalTakenRemaining = activeLoans
                .filter { it.type == LoanType.TAKEN.name }
                .sumOf { it.remainingBalance }
        )
    }

    fun filterByType(type: LoanType?) {
        _selectedType.value = type
    }

    fun filterByStatus(status: LoanStatus?) {
        _selectedStatus.value = status
    }

    fun deleteLoan(loanId: Long) {
        viewModelScope.launch {
            try {
                loanRepository.deleteLoan(loanId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete loan"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
