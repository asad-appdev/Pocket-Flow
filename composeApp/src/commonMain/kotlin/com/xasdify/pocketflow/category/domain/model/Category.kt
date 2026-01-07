package com.xasdify.pocketflow.category.domain.model

import com.xasdify.pocketflow.transactions.domain.model.TransactionType
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Category @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val name: String,
    val type: TransactionType, // To link category to INCOME or EXPENSE
    val iconName: String? = null,
    val colorCode: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
)