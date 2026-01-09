package com.xasdify.pocketflow.financialPlanning.data.repository

import com.xasdify.pocketflow.financialPlanning.data.dao.DebtDao
import com.xasdify.pocketflow.financialPlanning.data.entities.DebtEntity
import kotlinx.coroutines.flow.Flow

class DebtRepository(
    private val debtDao: DebtDao
) {
    fun getAllDebts(): Flow<List<DebtEntity>> = debtDao.getAllDebts()
    
    suspend fun getDebtById(id: Int): DebtEntity? = debtDao.getDebtById(id)
    
    fun getDebtsByStatus(isPaid: Boolean): Flow<List<DebtEntity>> =
        debtDao.getDebtsByStatus(isPaid)
    
    fun getTotalUnpaidDebts(): Flow<Double?> = debtDao.getTotalUnpaidDebts()
    
    fun getTotalPaidDebts(): Flow<Double?> = debtDao.getTotalPaidDebts()
    
    fun getOverdueCount(currentDate: Long): Flow<Int> = debtDao.getOverdueCount(currentDate)
    
    suspend fun insertDebt(debt: DebtEntity): Long = debtDao.insertDebt(debt)
    
    suspend fun updateDebt(debt: DebtEntity) = debtDao.updateDebt(debt)
    
    suspend fun deleteDebt(debt: DebtEntity) = debtDao.deleteDebt(debt)
    
    suspend fun deleteDebtById(id: Int) = debtDao.deleteDebtById(id)
    
    suspend fun updateDebtStatus(id: Int, isPaid: Boolean) = debtDao.updateDebtStatus(id, isPaid)
}