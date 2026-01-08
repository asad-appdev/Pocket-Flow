package com.xasdify.pocketflow.core.presentation.navigation.finance

sealed interface FinanceChild {

    class Budget(val component: BudgetScreenComponent) : FinanceChild
    class Debt(val component: DebtScreenComponent) : FinanceChild
    class Goal(val component: GoalScreenComponent) : FinanceChild
    class AddLoan(val component: com.arkivanov.decompose.ComponentContext) : FinanceChild
}