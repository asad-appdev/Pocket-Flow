package com.xasdify.pocketflow.transactions.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllIncome(): Flow<List<IncomeEntity>>
    
    @Query("SELECT * FROM income WHERE id = :id")
    suspend fun getIncomeById(id: Int): IncomeEntity?
    
    @Query("SELECT * FROM income WHERE category = :category ORDER BY date DESC")
    fun getIncomeByCategory(category: String): Flow<List<IncomeEntity>>
    
    @Query("SELECT * FROM income WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getIncomeByDateRange(startDate: Long, endDate: Long): Flow<List<IncomeEntity>>
    
    @Query("SELECT SUM(amount) FROM income")
    fun getTotalIncome(): Flow<Double?>
    
    @Query("SELECT SUM(amount) FROM income WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalIncomeByDateRange(startDate: Long, endDate: Long): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: IncomeEntity): Long
    
    @Update
    suspend fun updateIncome(income: IncomeEntity)
    
    @Delete
    suspend fun deleteIncome(income: IncomeEntity)
    
    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteIncomeById(id: Int)
    
    @Query("DELETE FROM income")
    suspend fun deleteAllIncome()
}