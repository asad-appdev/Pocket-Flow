package com.xasdify.pocketflow.analytics_reporting.domain.model

data class CategoryAnalysis(
    val category: String,             // e.g., "Food", "Transport"
    val totalSpent: Double,
    val percentage: Float             // % of total expenses
)
