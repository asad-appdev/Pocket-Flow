package com.xasdify.pocketflow.analytics_reporting.domain.model

import kotlinx.datetime.LocalDate

data class FinancialReport(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val incomeTotal: Double,
    val expenseTotal: Double,
    val netSavings: Double,
    val byCategory: List<CategoryAnalysis>
)