package com.xasdify.pocketflow.financialPlanning.data.repository

import com.xasdify.pocketflow.financialPlanning.data.dao.BudgetDao
import com.xasdify.pocketflow.financialPlanning.data.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

class BudgetRepository(
    private val budgetDao: BudgetDao
) {
    fun getAllBudgets(): Flow<List<BudgetEntity>> = budgetDao.getAllBudgets()
    
    suspend fun getBudgetById(id: Int): BudgetEntity? = budgetDao.getBudgetById(id)
    
    fun getBudgetsByCategory(category: String): Flow<List<BudgetEntity>> =
        budgetDao.getBudgetsByCategory(category)
    
    fun getActiveBudgets(currentDate: Long): Flow<List<BudgetEntity>> =
        budgetDao.getActiveBudgets(currentDate)
    
    fun getTotalActiveBudget(currentDate: Long): Flow<Double?> =
        budgetDao.getTotalActiveBudget(currentDate)
    
    suspend fun insertBudget(budget: BudgetEntity): Long = budgetDao.insertBudget(budget)
    
    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)
    
    suspend fun deleteBudget(budget: BudgetEntity) = budgetDao.deleteBudget(budget)
    
    suspend fun deleteBudgetById(id: Int) = budgetDao.deleteBudgetById(id)
}