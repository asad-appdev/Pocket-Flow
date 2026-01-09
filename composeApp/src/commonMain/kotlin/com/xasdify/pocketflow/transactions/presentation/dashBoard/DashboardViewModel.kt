package com.xasdify.pocketflow.transactions.presentation.dashBoard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.transactions.data.entities.ExpenseEntity
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity
import com.xasdify.pocketflow.transactions.data.repository.ExpenseRepository
import com.xasdify.pocketflow.transactions.data.repository.IncomeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardUiState(
    val recentExpenses: List<ExpenseEntity> = emptyList(),
    val recentIncome: List<IncomeEntity> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val netBalance: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DashboardViewModel(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState(isLoading = true))
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                combine(
                    expenseRepository.getAllExpenses(),
                    incomeRepository.getAllIncome(),
                    expenseRepository.getTotalExpenses(),
                    incomeRepository.getTotalIncome()
                ) { expenses, income, totalExp, totalInc ->
                    val totalExpenses = totalExp ?: 0.0
                    val totalIncome = totalInc ?: 0.0
                    
                    DashboardUiState(
                        recentExpenses = expenses.take(5),  // Show last 5
                        recentIncome = income.take(5),
                        totalExpenses = totalExpenses,
                        totalIncome = totalIncome,
                        netBalance = totalIncome - totalExpenses,
                        isLoading = false
                    )
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load dashboard data"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load dashboard data"
                )
            }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadDashboardData()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}