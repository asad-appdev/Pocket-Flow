package com.xasdify.pocketflow.core.presentation.navigation.finance

import kotlinx.serialization.Serializable

@Serializable
sealed class FinanceRoute() {
    @Serializable
    object Budget : FinanceRoute()

    @Serializable
    object Debt : FinanceRoute()

    @Serializable
    object Goals : FinanceRoute()
}