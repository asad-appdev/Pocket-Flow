package com.xasdify.pocketflow.core.presentation.navigation.main

import AddLoanScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsContent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceContent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeContent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileContent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionContent
import com.xasdify.pocketflow.financialPlanning.presentation.budget.AddBudgetScreen
import com.xasdify.pocketflow.financialPlanning.presentation.budget.BudgetScreen
import com.xasdify.pocketflow.financialPlanning.presentation.debt.DebtScreen
import com.xasdify.pocketflow.loans.presentation.detail.LoanDetailScreen
import kotlinx.coroutines.launch

@Composable
fun MainContent(root: MainComponent) {
    val stack by root.childStack.subscribeAsState()
    val activeChild = stack.active.instance

    val scope = rememberCoroutineScope()
    // val loanRepository: LoanRepository = koinInject()

    val showBottomBar = activeChild !is MainChild.AddBudget &&
            activeChild !is MainChild.AddLoan &&
            activeChild !is MainChild.LoanDetail

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = activeChild is MainChild.Transaction,
                        onClick = root::onTransactionsClicked,
                        icon = {
                            Icon(
                                Icons.Default.SwapHoriz,
                                contentDescription = "Transactions"
                            )
                        },
                        label = { Text("Transactions") }
                    )
                    NavigationBarItem(
                        selected = activeChild is MainChild.Budget,
                        onClick = root::onBudgetClicked,
                        icon = { Icon(Icons.Default.PieChart, contentDescription = "Budget") },
                        label = { Text("Budget") }
                    )
                    NavigationBarItem(
                        selected = activeChild is MainChild.Loans,
                        onClick = root::onLoansClicked,
                        icon = {
                            Icon(
                                Icons.Default.AccountBalanceWallet,
                                contentDescription = "Loans"
                            )
                        },
                        label = { Text("Loans") }
                    )
                    NavigationBarItem(
                        selected = activeChild is MainChild.Analytics,
                        onClick = root::onAnalyticsClicked,
                        icon = { Icon(Icons.Default.BarChart, contentDescription = "Analytics") },
                        label = { Text("Analytics") }
                    )
                    NavigationBarItem(
                        selected = activeChild is MainChild.Profile,
                        onClick = root::onProfileClicked,
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Children(stack = root.childStack) { child ->
                when (val inst = child.instance) {
                    is MainChild.Home -> HomeContent(inst.component) // Legacy/Hidden
                    is MainChild.Transaction -> TransactionContent(inst.component)
                    is MainChild.Budget -> BudgetScreen(inst.component)
                    is MainChild.Loans -> DebtScreen(inst.component)
                    is MainChild.Analytics -> AnalyticsContent(inst.component)
                    is MainChild.Profile -> ProfileContent(inst.component)

                    is MainChild.Finance -> FinanceContent(inst.component) // Legacy

                    is MainChild.AddBudget -> AddBudgetScreen(
                        component = inst.component,
                        onNavigateBack = root::onBackClicked,
                        onSave = { category, amount ->
                            // TODO: Handle save via component or repository
                        }
                    )

                    is MainChild.AddLoan -> AddLoanScreen(
                        context = inst.component,
                        onNavigateBack = root::onBackClicked,
                        onSave = { loan ->
                            scope.launch {
                                //  loanRepository.insertLoan(loan)
                            }
                        }
                    )

                    is MainChild.LoanDetail -> LoanDetailScreen(
                        loanId = inst.loanId,
                        onNavigateBack = root::onBackClicked
                    )
                }
            }
        }
    }
}