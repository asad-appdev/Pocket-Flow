package com.xasdify.pocketflow.transactions.presentation.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.transactions.data.entities.ExpenseEntity
import com.xasdify.pocketflow.transactions.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ExpenseUiState(
    val expenses: List<ExpenseEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String? = null,
    val totalExpenses: Double = 0.0,
    val showAddDialog: Boolean = false
)

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState(isLoading = true))
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)

    init {
        loadExpenses()
        loadTotalExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            try {
                combine(
                    repository.getAllExpenses(),
                    _selectedCategory
                ) { allExpenses, category ->
                    if (category != null) {
                        allExpenses.filter { it.category == category }
                    } else {
                        allExpenses
                    }
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load expenses"
                    )
                }.collect { filteredExpenses ->
                    _uiState.value = _uiState.value.copy(
                        expenses = filteredExpenses,
                        isLoading = false,
                        selectedCategory = _selectedCategory.value
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load expenses"
                )
            }
        }
    }

    private fun loadTotalExpenses() {
        viewModelScope.launch {
            repository.getTotalExpenses()
                .collect { total ->
                    _uiState.value = _uiState.value.copy(
                        totalExpenses = total ?: 0.0
                    )
                }
        }
    }

    fun filterByCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            try {
                repository.deleteExpense(expense)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete expense"
                )
            }
        }
    }

    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }

    fun hideAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}