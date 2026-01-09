package com.xasdify.pocketflow.transactions.data.repository

import com.xasdify.pocketflow.transactions.data.dao.ExpenseDao
import com.xasdify.pocketflow.transactions.data.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()
    
    suspend fun getExpenseById(id: Int): ExpenseEntity? = expenseDao.getExpenseById(id)
    
    fun getExpensesByCategory(category: String): Flow<List<ExpenseEntity>> = 
        expenseDao.getExpensesByCategory(category)
    
    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<ExpenseEntity>> = 
        expenseDao.getExpensesByDateRange(startDate, endDate)
    
    fun getTotalExpenses(): Flow<Double?> = expenseDao.getTotalExpenses()
    
    fun getTotalExpensesByDateRange(startDate: Long, endDate: Long): Flow<Double?> = 
        expenseDao.getTotalExpensesByDateRange(startDate, endDate)
    
    suspend fun insertExpense(expense: ExpenseEntity): Long = expenseDao.insertExpense(expense)
    
    suspend fun updateExpense(expense: ExpenseEntity) = expenseDao.updateExpense(expense)
    
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    
    suspend fun deleteExpenseById(id: Int) = expenseDao.deleteExpenseById(id)
}