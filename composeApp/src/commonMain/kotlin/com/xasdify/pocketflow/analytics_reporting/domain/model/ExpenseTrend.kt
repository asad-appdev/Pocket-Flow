package com.xasdify.pocketflow.analytics_reporting.domain.model

data class ExpenseTrend(
    val month: String,                // e.g., "May", "June"
    val totalExpense: Double
)