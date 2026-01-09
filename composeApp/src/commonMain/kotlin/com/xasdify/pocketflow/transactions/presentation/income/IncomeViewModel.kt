package com.xasdify.pocketflow.transactions.presentation.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity
import com.xasdify.pocketflow.transactions.data.repository.IncomeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class IncomeUiState(
    val incomes: List<IncomeEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String? = null,
    val totalIncome: Double = 0.0,
    val showAddDialog: Boolean = false
)

class IncomeViewModel(
    private val repository: IncomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(IncomeUiState(isLoading = true))
    val uiState: StateFlow<IncomeUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)

    init {
        loadIncome()
        loadTotalIncome()
    }

    private fun loadIncome() {
        viewModelScope.launch {
            try {
                combine(
                    repository.getAllIncome(),
                    _selectedCategory
                ) { allIncome, category ->
                    if (category != null) {
                        allIncome.filter { it.category == category }
                    } else {
                        allIncome
                    }
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load income"
                    )
                }.collect { filteredIncome ->
                    _uiState.value = _uiState.value.copy(
                        incomes = filteredIncome,
                        isLoading = false,
                        selectedCategory = _selectedCategory.value
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load income"
                )
            }
        }
    }

    private fun loadTotalIncome() {
        viewModelScope.launch {
            repository.getTotalIncome()
                .collect { total ->
                    _uiState.value = _uiState.value.copy(
                        totalIncome = total ?: 0.0
                    )
                }
        }
    }

    fun filterByCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun deleteIncome(income: IncomeEntity) {
        viewModelScope.launch {
            try {
                repository.deleteIncome(income)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete income"
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