package com.xasdify.pocketflow.financialPlanning.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xasdify.pocketflow.financialPlanning.data.entities.DebtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {
    @Query("SELECT * FROM debts ORDER BY dueDate ASC")
    fun getAllDebts(): Flow<List<DebtEntity>>
    
    @Query("SELECT * FROM debts WHERE id = :id")
    suspend fun getDebtById(id: Int): DebtEntity?
    
    @Query("SELECT * FROM debts WHERE isPaid = :isPaid ORDER BY dueDate ASC")
    fun getDebtsByStatus(isPaid: Boolean): Flow<List<DebtEntity>>
    
    @Query("SELECT SUM(amount) FROM debts WHERE isPaid = 0")
    fun getTotalUnpaidDebts(): Flow<Double?>
    
    @Query("SELECT SUM(amount) FROM debts WHERE isPaid = 1")
    fun getTotalPaidDebts(): Flow<Double?>
    
    @Query("SELECT COUNT(*) FROM debts WHERE isPaid = 0 AND dueDate < :currentDate")
    fun getOverdueCount(currentDate: Long): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDebt(debt: DebtEntity): Long
    
    @Update
    suspend fun updateDebt(debt: DebtEntity)
    
    @Delete
    suspend fun deleteDebt(debt: DebtEntity)
    
    @Query("DELETE FROM debts WHERE id = :id")
    suspend fun deleteDebtById(id: Int)
    
    @Query("UPDATE debts SET isPaid = :isPaid WHERE id = :id")
    suspend fun updateDebtStatus(id: Int, isPaid: Boolean)
}