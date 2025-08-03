package com.xasdify.pocketflow.core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.xasdify.pocketflow.financialPlanning.data.entities.BudgetEntity
import com.xasdify.pocketflow.financialPlanning.data.entities.DebtEntity
import com.xasdify.pocketflow.financialPlanning.data.entities.GoalEntity
import com.xasdify.pocketflow.profile.data.entities.UserProfileEntity
import com.xasdify.pocketflow.transactions.data.entities.CategoryEntity
import com.xasdify.pocketflow.transactions.data.entities.ExpenseEntity
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity

@Database(
    entities = [
        UserProfileEntity::class,
        BudgetEntity::class,
        DebtEntity::class,
        GoalEntity::class,
        CategoryEntity::class,
        ExpenseEntity::class,
        IncomeEntity::class,
    ],
    version = 1
)

@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        const val DB_NAME = "app_database.db"
    }
}