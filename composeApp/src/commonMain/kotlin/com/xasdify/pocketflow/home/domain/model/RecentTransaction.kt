package com.xasdify.pocketflow.home.domain.model

import kotlinx.datetime.LocalDateTime

data class RecentTransaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val date: LocalDateTime,
    val category: String,
    //val type: TransactionType // Expense or Income
)