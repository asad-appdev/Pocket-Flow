package com.xasdify.pocketflow.core.presentation.navigation.transaction

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.transactions.presentation.dashBoard.DashboardScreen
import com.xasdify.pocketflow.transactions.presentation.expenses.ExpenseScreen
import com.xasdify.pocketflow.transactions.presentation.income.IncomeScreen

@Composable
fun TransactionContent(component: TransactionComponent) {
    Children(stack = component.stack) {
        when (val child = it.instance) {
            is TransactionChild.DashBoard -> DashboardScreen(child.component)
            is TransactionChild.Expense -> ExpenseScreen(child.component)
            is TransactionChild.Income -> IncomeScreen(child.component)
        }
    }
}