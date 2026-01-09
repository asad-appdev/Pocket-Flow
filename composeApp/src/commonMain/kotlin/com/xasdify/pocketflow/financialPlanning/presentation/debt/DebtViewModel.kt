package com.xasdify.pocketflow.financialPlanning.presentation.debt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.financialPlanning.data.entities.DebtEntity
import com.xasdify.pocketflow.financialPlanning.data.repository.DebtRepository
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DebtUiState(
    val debts: List<DebtEntity> = emptyList(),
    val unpaidDebts: List<DebtEntity> = emptyList(),
    val totalUnpaidAmount: Double = 0.0,
    val overdueCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DebtViewModel(
    private val repository: DebtRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DebtUiState(isLoading = true))
    val uiState: StateFlow<DebtUiState> = _uiState.asStateFlow()

    init {
        loadDebts()
    }

    private fun loadDebts() {
        viewModelScope.launch {
            try {
                val currentDate = getCurrentTimeMilli()
                combine(
                    repository.getAllDebts(),
                    repository.getDebtsByStatus(false),
                    repository.getTotalUnpaidDebts(),
                    repository.getOverdueCount(currentDate)
                ) { allDebts, unpaid, totalUnpaid, overdue ->
                    DebtUiState(
                        debts = allDebts,
                        unpaidDebts = unpaid,
                        totalUnpaidAmount = totalUnpaid ?: 0.0,
                        overdueCount = overdue,
                        isLoading = false
                    )
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load debts"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load debts"
                )
            }
        }
    }

    fun toggleDebtStatus(debt: DebtEntity) {
        viewModelScope.launch {
            try {
                repository.updateDebtStatus(debt.id, !debt.isPaid)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update debt"
                )
            }
        }
    }

    fun deleteDebt(debt: DebtEntity) {
        viewModelScope.launch {
            try {
                repository.deleteDebt(debt)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete debt"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}