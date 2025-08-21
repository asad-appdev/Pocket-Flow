package com.xasdify.pocketflow.core.presentation.navigation.transaction

sealed interface TransactionChild {
    class DashBoard(val component: TransactionDashBoardScreenComponent) : TransactionChild
    class Expense(val component: ExpenseScreenComponent) : TransactionChild
    class Income(val component: IncomeScreenComponent) : TransactionChild
}