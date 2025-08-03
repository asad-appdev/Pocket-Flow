package com.xasdify.pocketflow.financialPlanning.domain.model

import kotlinx.datetime.LocalDate

data class Debt(
    val id: Int,
    val name: String,
    val amount: Double,
    val dueDate: LocalDate,
    val isPaid: Boolean
)