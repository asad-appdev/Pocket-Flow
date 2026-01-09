package com.xasdify.pocketflow.financialPlanning.presentation.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xasdify.pocketflow.financialPlanning.data.entities.GoalEntity
import com.xasdify.pocketflow.financialPlanning.data.repository.GoalRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class GoalsUiState(
    val goals: List<GoalEntity> = emptyList(),
    val activeGoals: List<GoalEntity> = emptyList(),
    val completedGoals: List<GoalEntity> = emptyList(),
    val totalGoalAmount: Double = 0.0,
    val totalSaved: Double = 0.0,
    val overallProgress: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null
)

class GoalsViewModel(
    private val repository: GoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalsUiState(isLoading = true))
    val uiState: StateFlow<GoalsUiState> = _uiState.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        viewModelScope.launch {
            try {
                combine(
                    repository.getAllGoals(),
                    repository.getActiveGoals(),
                    repository.getCompletedGoals(),
                    repository.getTotalGoalAmount(),
                    repository.getTotalSavedAmount()
                ) { allGoals, active, completed, totalGoal, totalSaved ->
                    val goalAmount = totalGoal ?: 0.0
                    val saved = totalSaved ?: 0.0
                    val progress = if (goalAmount > 0) (saved / goalAmount).toFloat() else 0f
                    
                    GoalsUiState(
                        goals = allGoals,
                        activeGoals = active,
                        completedGoals = completed,
                        totalGoalAmount = goalAmount,
                        totalSaved = saved,
                        overallProgress = progress,
                        isLoading = false
                    )
                }.catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load goals"
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load goals"
                )
            }
        }
    }

    fun updateSavedAmount(goalId: Int, newAmount: Double) {
        viewModelScope.launch {
            try {
                repository.updateSavedAmount(goalId, newAmount)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to update goal"
                )
            }
        }
    }

    fun deleteGoal(goal: GoalEntity) {
        viewModelScope.launch {
            try {
                repository.deleteGoal(goal)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to delete goal"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}