package com.xasdify.pocketflow.financialPlanning.presentation.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.financialPlanning.data.entities.BudgetEntity
import com.xasdify.pocketflow.financialPlanning.data.repository.BudgetRepository
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class BudgetUiState(
    val budgets: List<BudgetEntity> = emptyList(),
    val activeBudgets: List<BudgetEntity> = emptyList(),
    val totalActiveBudget: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class BudgetViewModel(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BudgetUiState(isLoading = true))
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    init {
        loadBudgets()
    }

    private fun loadBudgets() {
        viewModelScope.launch {
            try {
                val currentDate = getCurrentTimeMilli()
                combine(
                    repository.getAllBudgets(),
                    repository.getActiveBudgets(currentDate),
                    repository.getTotalActiveBudget(currentDate)
                ) { allBudgets, activeBudgets, totalBudget ->
                    BudgetUiState(
                        budgets = allBudgets,
                        activeBudgets = activeBudgets,
                        totalActiveBudget = totalBudget ?: 0.0,
                        isLoading = false
                    )
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load budgets"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value =_uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load budgets"
                )
            }
        }
    }

    fun deleteBudget(budget: BudgetEntity) {
        viewModelScope.launch {
            try {
                repository.deleteBudget(budget)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete budget"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}