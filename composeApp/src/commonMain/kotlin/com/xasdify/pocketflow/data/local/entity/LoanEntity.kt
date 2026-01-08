package com.xasdify.pocketflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loans")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // TAKEN or GIVEN
    val lenderName: String, // For TAKEN: who lent to you; For GIVEN: who you lent to
    val principalAmount: Double,
    val interestRate: Double, // Annual percentage
    val currencyCode: String,
    val startDate: Long,
    val dueDate: Long?,
    val status: String, // ACTIVE or CLOSED
    val description: String?,
    val createdAt: Long,
    val updatedAt: Long
)
