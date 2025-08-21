package com.xasdify.pocketflow.core.presentation.navigation.transaction

import kotlinx.serialization.Serializable

@Serializable
sealed class TransactionRoute() {
    @Serializable
    object Dashboard : TransactionRoute()

    @Serializable
    object Expense : TransactionRoute()

    @Serializable
    object Income : TransactionRoute()
}