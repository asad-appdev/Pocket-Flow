package com.xasdify.pocketflow.transactions.data.repository

import com.xasdify.pocketflow.transactions.data.dao.IncomeDao
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity
import kotlinx.coroutines.flow.Flow

class IncomeRepository(
    private val incomeDao: IncomeDao
) {
    fun getAllIncome(): Flow<List<IncomeEntity>> = incomeDao.getAllIncome()
    
    suspend fun getIncomeById(id: Int): IncomeEntity? = incomeDao.getIncomeById(id)
    
    fun getIncomeByCategory(category: String): Flow<List<IncomeEntity>> = 
        incomeDao.getIncomeByCategory(category)
    
    fun getIncomeByDateRange(startDate: Long, endDate: Long): Flow<List<IncomeEntity>> = 
        incomeDao.getIncomeByDateRange(startDate, endDate)
    
    fun getTotalIncome(): Flow<Double?> = incomeDao.getTotalIncome()
    
    fun getTotalIncomeByDateRange(startDate: Long, endDate: Long): Flow<Double?> = 
        incomeDao.getTotalIncomeByDateRange(startDate, endDate)
    
    suspend fun insertIncome(income: IncomeEntity): Long = incomeDao.insertIncome(income)
    
    suspend fun updateIncome(income: IncomeEntity) = incomeDao.updateIncome(income)
    
    suspend fun deleteIncome(income: IncomeEntity) = incomeDao.deleteIncome(income)
    
    suspend fun deleteIncomeById(id: Int) = incomeDao.deleteIncomeById(id)
}