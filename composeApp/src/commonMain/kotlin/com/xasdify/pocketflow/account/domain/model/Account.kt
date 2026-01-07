package com.xasdify.pocketflow.account.domain.model

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

enum class AccountType {
    CASH, BANK, CREDIT_CARD, SAVINGS, OTHER
}

data class Account @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val name: String,
    val type: AccountType,
    val initialBalance: Double = 0.0,
    val currencyCode: String, // Should ideally be linked to a global setting or per account
    val iconName: String? = null,
    val colorCode: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds()
)