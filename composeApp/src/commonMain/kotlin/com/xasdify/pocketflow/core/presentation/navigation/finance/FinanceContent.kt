package com.xasdify.pocketflow.core.presentation.navigation.finance

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.financialPlanning.presentation.budget.BudgetScreen
import com.xasdify.pocketflow.financialPlanning.presentation.debt.DebtScreen
import com.xasdify.pocketflow.financialPlanning.presentation.goals.GoalsScreen

@Composable
fun FinanceContent(component: FinanceComponent) {
    Children(stack = component.stack) {
        when (val c = it.instance) {
            is FinanceChild.Budget -> BudgetScreen(c.component)
            is FinanceChild.Debt -> DebtScreen(c.component)
            is FinanceChild.Goal -> GoalsScreen(c.component)
        }

    }


}