package com.xasdify.pocketflow.core.presentation.navigation.finance

import com.arkivanov.decompose.ComponentContext

class BudgetScreenComponent(
    val context: ComponentContext,
    val onAddBudget: () -> Unit
) : ComponentContext by context {
    fun onAddBudgetClicked() = onAddBudget()
}