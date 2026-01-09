package com.xasdify.pocketflow.financialPlanning.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xasdify.pocketflow.financialPlanning.data.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets ORDER BY startDate DESC")
    fun getAllBudgets(): Flow<List<BudgetEntity>>
    
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Int): BudgetEntity?
    
    @Query("SELECT * FROM budgets WHERE category = :category")
    fun getBudgetsByCategory(category: String): Flow<List<BudgetEntity>>
    
    @Query("SELECT * FROM budgets WHERE :currentDate BETWEEN startDate AND endDate")
    fun getActiveBudgets(currentDate: Long): Flow<List<BudgetEntity>>
    
    @Query("SELECT SUM(amount) FROM budgets WHERE :currentDate BETWEEN startDate AND endDate")
    fun getTotalActiveBudget(currentDate: Long): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity): Long
    
    @Update
    suspend fun updateBudget(budget: BudgetEntity)
    
    @Delete
    suspend fun deleteBudget(budget: BudgetEntity)
    
    @Query("DELETE FROM budgets WHERE id = :id")
    suspend fun deleteBudgetById(id: Int)
    
    @Query("DELETE FROM budgets")
    suspend fun deleteAllBudgets()
}