package com.xasdify.pocketflow.core.presentation.navigation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceComponent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeComponent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileComponent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionComponent

import com.arkivanov.decompose.router.stack.bringToFront
import com.xasdify.pocketflow.core.presentation.navigation.finance.BudgetScreenComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.DebtScreenComponent
import com.arkivanov.decompose.router.stack.pop

class MainComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    val navigation = StackNavigation<MainRoute>()

    val childStack: Value<ChildStack<MainRoute, MainChild>> =
        childStack(
            source = navigation,
            serializer = MainRoute.serializer(),
            initialConfiguration = MainRoute.Transactions, // Set Transactions as default or Home? User said "convert dashboard items"
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: MainRoute, context: ComponentContext): MainChild =
        when (route) {
            MainRoute.Home -> MainChild.Home(HomeComponent(context) {
                navigation.pushNew(it)
            })

            MainRoute.Transactions -> MainChild.Transaction(TransactionComponent(context))
            MainRoute.Finance -> MainChild.Finance(FinanceComponent(context)) // Legacy
            MainRoute.Analytics -> MainChild.Analytics(AnalyticsComponent(context))
            MainRoute.Profile -> MainChild.Profile(ProfileComponent(context))
            
            MainRoute.Budget -> MainChild.Budget(BudgetScreenComponent(context) {
                navigation.pushNew(MainRoute.AddBudget)
            })
            
            MainRoute.Loans -> MainChild.Loans(DebtScreenComponent(context, 
                onAddLoan = { navigation.pushNew(MainRoute.AddLoan) },
                onLoanClicked = { id -> navigation.pushNew(MainRoute.LoanDetail(id)) }
            ))
            
            MainRoute.AddBudget -> MainChild.AddBudget(context)
            MainRoute.AddLoan -> MainChild.AddLoan(context)
            is MainRoute.LoanDetail -> MainChild.LoanDetail(context, route.loanId)
        }

    fun onHomeClicked() {
        navigation.bringToFront(MainRoute.Home)
    }

    fun onTransactionsClicked() {
        navigation.bringToFront(MainRoute.Transactions)
    }

    fun onBudgetClicked() {
        navigation.bringToFront(MainRoute.Budget)
    }

    fun onLoansClicked() {
        navigation.bringToFront(MainRoute.Loans)
    }

    fun onAnalyticsClicked() {
        navigation.bringToFront(MainRoute.Analytics)
    }

    fun onProfileClicked() {
        navigation.bringToFront(MainRoute.Profile)
    }
    
    fun onBackClicked() {
        navigation.pop()
    }
}