package com.xasdify.pocketflow.home.domain.model

data class BudgetOverview(
    val category: String,
    val amountSpent: Double,
    val totalBudget: Double
)

