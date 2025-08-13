package com.xasdify.pocketflow.core.presentation.navigation.finance

sealed class FinanceRoute() {
    object Budget : FinanceRoute()
    object Debt : FinanceRoute()
    object Goals : FinanceRoute()
}