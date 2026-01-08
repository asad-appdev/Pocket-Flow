package com.xasdify.pocketflow.core.presentation.navigation.main

import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceComponent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeComponent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileComponent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionComponent


import com.xasdify.pocketflow.core.presentation.navigation.finance.BudgetScreenComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.DebtScreenComponent
import com.arkivanov.decompose.ComponentContext

sealed interface MainChild {
    class Home(val component: HomeComponent) : MainChild
    class Transaction(val component: TransactionComponent) : MainChild
    class Finance(val component: FinanceComponent) : MainChild
    class Analytics(val component: AnalyticsComponent) : MainChild
    class Profile(val component: ProfileComponent) : MainChild
    class Budget(val component: BudgetScreenComponent) : MainChild
    class Loans(val component: DebtScreenComponent) : MainChild
    class AddBudget(val component: ComponentContext) : MainChild
    class AddLoan(val component: ComponentContext) : MainChild
    class LoanDetail(val component: ComponentContext, val loanId: Long) : MainChild
}