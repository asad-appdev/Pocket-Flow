package com.xasdify.pocketflow.budget.domain.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Budget @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val name: String,
    val categoryId: Long, // Or null for an overall budget, or a list of categoryIds
    val amountLimit: Double,
    val startDate: Long,
    val endDate: Long,
    val alertThresholdPercent: Double? = null, // e.g., 0.8 for 80%
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
)