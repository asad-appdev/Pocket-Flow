package com.xasdify.pocketflow.transactions.domain.model

import kotlinx.datetime.LocalDateTime

data class Income(
    val id: Int,
    val title: String,
    val amount: Double,
    val category: String,
    val date: LocalDateTime,
    val notes: String?
)