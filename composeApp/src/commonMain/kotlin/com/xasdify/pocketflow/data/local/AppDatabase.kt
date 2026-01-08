package com.xasdify.pocketflow.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.xasdify.pocketflow.data.local.dao.BackupDao
import com.xasdify.pocketflow.data.local.dao.LoanDao
import com.xasdify.pocketflow.data.local.dao.LoanPaymentDao
import com.xasdify.pocketflow.data.local.dao.PresetDao
import com.xasdify.pocketflow.data.local.dao.TagDao
import com.xasdify.pocketflow.data.local.entity.BackupMetadataEntity
import com.xasdify.pocketflow.data.local.entity.LoanEntity
import com.xasdify.pocketflow.data.local.entity.LoanPaymentEntity
import com.xasdify.pocketflow.data.local.entity.PresetEntity
import com.xasdify.pocketflow.data.local.entity.PresetTagCrossRef
import com.xasdify.pocketflow.data.local.entity.TagEntity
import com.xasdify.pocketflow.financialPlanning.data.entities.BudgetEntity
import com.xasdify.pocketflow.financialPlanning.data.entities.DebtEntity
import com.xasdify.pocketflow.financialPlanning.data.entities.GoalEntity
import com.xasdify.pocketflow.profile.data.entities.UserProfileEntity
import com.xasdify.pocketflow.transactions.data.entities.CategoryEntity
import com.xasdify.pocketflow.transactions.data.entities.ExpenseEntity
import com.xasdify.pocketflow.transactions.data.entities.IncomeEntity

@Database(
    entities = [
        PresetEntity::class,
        TagEntity::class,
        PresetTagCrossRef::class,
        BackupMetadataEntity::class,
        UserProfileEntity::class,
        BudgetEntity::class,
        DebtEntity::class,
        GoalEntity::class,
        CategoryEntity::class,
        ExpenseEntity::class,
        IncomeEntity::class,
        LoanEntity::class,
        LoanPaymentEntity::class,
    ],
    version = 2
)

@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun presetDao(): PresetDao
    abstract fun tagDao(): TagDao
    abstract fun backupDao(): BackupDao
    abstract fun getLoanDao(): LoanDao
    abstract fun getLoanPaymentDao(): LoanPaymentDao

    companion object {
        val DB_NAME: String = "pocketflow.db"
    }


}