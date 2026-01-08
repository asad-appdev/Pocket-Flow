package com.xasdify.pocketflow.core.presentation.navigation.finance

import AddLoanScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.pop
import com.xasdify.pocketflow.financialPlanning.presentation.budget.BudgetScreen
import com.xasdify.pocketflow.financialPlanning.presentation.debt.DebtScreen
import com.xasdify.pocketflow.financialPlanning.presentation.goals.GoalsScreen

@Composable
fun FinanceContent(component: FinanceComponent) {
    val stack by component.stack.subscribeAsState()
    val activeChild = stack.active.instance
    val showBottomBar = activeChild !is FinanceChild.AddLoan

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = activeChild is FinanceChild.Budget,
                        onClick = component::onBudgetClicked,
                        icon = { Icon(Icons.Default.PieChart, contentDescription = "Budget") },
                        label = { Text("Budget") }
                    )
                    NavigationBarItem(
                        selected = activeChild is FinanceChild.Debt,
                        onClick = component::onDebtClicked,
                        icon = {
                            Icon(
                                Icons.Default.AccountBalanceWallet,
                                contentDescription = "Loans"
                            )
                        },
                        label = { Text("Loans") }
                    )
                    NavigationBarItem(
                        selected = activeChild is FinanceChild.Goal,
                        onClick = component::onGoalsClicked,
                        icon = { Icon(Icons.Default.Flag, contentDescription = "Goals") },
                        label = { Text("Goals") }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Children(stack = component.stack) {
                when (val c = it.instance) {
                    is FinanceChild.Budget -> BudgetScreen(c.component)
                    is FinanceChild.Debt -> DebtScreen(c.component)
                    is FinanceChild.Goal -> GoalsScreen(c.component)
                    is FinanceChild.AddLoan -> AddLoanScreen(
                        c.component,

                        onNavigateBack = { component.navigation.pop() }
                    )
                }
            }
        }
    }
}