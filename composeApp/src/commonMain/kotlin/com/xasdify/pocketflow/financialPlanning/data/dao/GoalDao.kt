package com.xasdify.pocketflow.financialPlanning.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xasdify.pocketflow.financialPlanning.data.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals ORDER BY deadline ASC")
    fun getAllGoals(): Flow<List<GoalEntity>>
    
    @Query("SELECT * FROM goals WHERE id = :id")
    suspend fun getGoalById(id: Int): GoalEntity?
    
    @Query("SELECT * FROM goals WHERE savedAmount >= targetAmount")
    fun getCompletedGoals(): Flow<List<GoalEntity>>
    
    @Query("SELECT * FROM goals WHERE savedAmount < targetAmount ORDER BY deadline ASC")
    fun getActiveGoals(): Flow<List<GoalEntity>>
    
    @Query("SELECT SUM(targetAmount) FROM goals WHERE savedAmount < targetAmount")
    fun getTotalGoalAmount(): Flow<Double?>
    
    @Query("SELECT SUM(savedAmount) FROM goals")
    fun getTotalSavedAmount(): Flow<Double?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity): Long
    
    @Update
    suspend fun updateGoal(goal: GoalEntity)
    
    @Delete
    suspend fun deleteGoal(goal: GoalEntity)
    
    @Query("DELETE FROM goals WHERE id = :id")
    suspend fun deleteGoalById(id: Int)
    
    @Query("UPDATE goals SET savedAmount = :newAmount WHERE id = :id")
    suspend fun updateSavedAmount(id: Int, newAmount: Double)
}