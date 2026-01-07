package com.xasdify.pocketflow.budget.domain.repository

import com.xasdify.pocketflow.budget.domain.model.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getAllBudgets(): Flow<List<Budget>>
    fun getBudgetById(id: Long): Flow<Budget?>
    fun getBudgetsForCategory(categoryId: Long): Flow<List<Budget>>
    fun getActiveBudgets(currentDate: Long): Flow<List<Budget>>
    suspend fun addBudget(budget: Budget): Long
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budgetId: Long)
}