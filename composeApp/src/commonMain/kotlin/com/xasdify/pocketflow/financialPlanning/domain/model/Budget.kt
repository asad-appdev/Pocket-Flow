package com.xasdify.pocketflow.financialPlanning.domain.model

import kotlinx.datetime.LocalDate

data class Budget(
    val id: Int,
    val category: String,
    val amount: Double,
    val startDate: LocalDate,
    val endDate: LocalDate
)



