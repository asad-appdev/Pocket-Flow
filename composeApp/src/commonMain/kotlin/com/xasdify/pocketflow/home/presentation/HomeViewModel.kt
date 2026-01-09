package com.xasdify.pocketflow.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.financialPlanning.data.repository.BudgetRepository
import com.xasdify.pocketflow.financialPlanning.data.repository.DebtRepository
import com.xasdify.pocketflow.financialPlanning.data.repository.GoalRepository
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.transactions.data.repository.ExpenseRepository
import com.xasdify.pocketflow.transactions.data.repository.IncomeRepository
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HomeUiState(
    val totalIncome: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val netBalance: Double = 0.0,
    val activeBudget: Double = 0.0,
    val totalDebts: Double = 0.0,
    val totalGoals: Double = 0.0,
    val totalLoans: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository,
    private val budgetRepository: BudgetRepository,
    private val debtRepository: DebtRepository,
    private val goalRepository: GoalRepository,
    private val loanRepository: LoanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                val currentDate = getCurrentTimeMilli()
                
                // Combine first 5 flows
                val financialDataFlow = combine(
                    incomeRepository.getTotalIncome(),
                    expenseRepository.getTotalExpenses(),
                    budgetRepository.getTotalActiveBudget(currentDate),
                    debtRepository.getTotalUnpaidDebts(),
                    goalRepository.getTotalGoalAmount()
                ) { income, expenses, budget, debts, goals ->
                    FinancialData(
                        income = income ?: 0.0,
                        expenses = expenses ?: 0.0,
                        budget = budget ?: 0.0,
                        debts = debts ?: 0.0,
                        goals = goals ?: 0.0
                    )
                }
                
                // Combine with loans flow
                combine(
                    financialDataFlow,
                    loanRepository.getTotalLoanAmount()
                ) { financial, loans ->
                    HomeUiState(
                        totalIncome = financial.income,
                        totalExpenses = financial.expenses,
                        netBalance = financial.income - financial.expenses,
                        activeBudget = financial.budget,
                        totalDebts = financial.debts,
                        totalGoals = financial.goals,
                        totalLoans = loans ?: 0.0,
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
    
    // Helper data class for intermediate combine
    private data class FinancialData(
        val income: Double,
        val expenses: Double,
        val budget: Double,
        val debts: Double,
        val goals: Double
    )

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadDashboardData()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}