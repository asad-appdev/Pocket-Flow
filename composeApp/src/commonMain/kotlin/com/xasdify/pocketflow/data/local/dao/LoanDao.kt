package com.xasdify.pocketflow.data.local.dao

import androidx.room.*
import com.xasdify.pocketflow.data.local.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Query("SELECT * FROM loans ORDER BY createdAt DESC")
    fun getAllLoans(): Flow<List<LoanEntity>>
    
    @Query("SELECT * FROM loans WHERE type = :type ORDER BY createdAt DESC")
    fun getLoansByType(type: String): Flow<List<LoanEntity>>
    
    @Query("SELECT * FROM loans WHERE status = :status ORDER BY createdAt DESC")
    fun getLoansByStatus(status: String): Flow<List<LoanEntity>>
    
    @Query("SELECT * FROM loans WHERE type = :type AND status = :status ORDER BY createdAt DESC")
    fun getLoansByTypeAndStatus(type: String, status: String): Flow<List<LoanEntity>>
    
    @Query("SELECT * FROM loans WHERE id = :id")
    suspend fun getLoanById(id: Long): LoanEntity?
    
    @Query("SELECT * FROM loans WHERE id = :id")
    fun getLoanByIdFlow(id: Long): Flow<LoanEntity?>
    
    @Insert
    suspend fun insertLoan(loan: LoanEntity): Long
    
    @Update
    suspend fun updateLoan(loan: LoanEntity)
    
    @Delete
    suspend fun deleteLoan(loan: LoanEntity)
    
    @Query("DELETE FROM loans WHERE id = :id")
    suspend fun deleteLoanById(id: Long)
}
