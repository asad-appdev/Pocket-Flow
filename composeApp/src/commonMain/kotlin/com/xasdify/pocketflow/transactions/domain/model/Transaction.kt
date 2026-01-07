package com.xasdify.pocketflow.transactions.domain.model

import kotlin.time.Clock // Added import
import kotlin.time.ExperimentalTime

enum class TransactionType {
    INCOME, EXPENSE
}

data class Transaction @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0, // 0 for new, DB will assign
    val type: TransactionType,
    val amount: Double,
    val currencyCode: String,
    val categoryId: Long,
    val accountId: Long,
    val transactionDate: Long,
    val description: String? = null,
    val notes: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(), // Corrected
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds()  // Corrected
)