package com.xasdify.pocketflow.financialPlanning.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "debts")
data class DebtEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val dueDate: Long,
    val isPaid: Boolean
)