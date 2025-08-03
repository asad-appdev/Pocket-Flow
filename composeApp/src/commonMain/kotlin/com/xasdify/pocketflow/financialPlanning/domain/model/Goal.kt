package com.xasdify.pocketflow.financialPlanning.domain.model

import kotlinx.datetime.LocalDate

data class Goal(
    val id: Int,
    val title: String,
    val targetAmount: Double,
    val savedAmount: Double,
    val deadline: LocalDate
)