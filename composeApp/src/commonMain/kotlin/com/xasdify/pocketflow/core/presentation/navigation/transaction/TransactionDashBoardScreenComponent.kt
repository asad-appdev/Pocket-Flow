package com.xasdify.pocketflow.core.presentation.navigation.transaction

import com.arkivanov.decompose.ComponentContext

class TransactionDashBoardScreenComponent(
    context: ComponentContext,
    val onNavigateToExpense: () -> Unit,
    val onNavigateToIncome: () -> Unit,
    val onBack: () -> Unit
) : ComponentContext by context {
}