package com.xasdify.pocketflow.financialPlanning.data.repository

import com.xasdify.pocketflow.financialPlanning.data.dao.GoalDao
import com.xasdify.pocketflow.financialPlanning.data.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

class GoalRepository(
    private val goalDao: GoalDao
) {
    fun getAllGoals(): Flow<List<GoalEntity>> = goalDao.getAllGoals()
    
    suspend fun getGoalById(id: Int): GoalEntity? = goalDao.getGoalById(id)
    
    fun getCompletedGoals(): Flow<List<GoalEntity>> = goalDao.getCompletedGoals()
    
    fun getActiveGoals(): Flow<List<GoalEntity>> = goalDao.getActiveGoals()
    
    fun getTotalGoalAmount(): Flow<Double?> = goalDao.getTotalGoalAmount()
    
    fun getTotalSavedAmount(): Flow<Double?> = goalDao.getTotalSavedAmount()
    
    suspend fun insertGoal(goal: GoalEntity): Long = goalDao.insertGoal(goal)
    
    suspend fun updateGoal(goal: GoalEntity) = goalDao.updateGoal(goal)
    
    suspend fun deleteGoal(goal: GoalEntity) = goalDao.deleteGoal(goal)
    
    suspend fun deleteGoalById(id: Int) = goalDao.deleteGoalById(id)
    
    suspend fun updateSavedAmount(id: Int, newAmount: Double) = 
        goalDao.updateSavedAmount(id, newAmount)
}