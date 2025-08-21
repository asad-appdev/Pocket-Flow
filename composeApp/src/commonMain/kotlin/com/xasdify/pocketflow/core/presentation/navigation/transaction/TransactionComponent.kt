package com.xasdify.pocketflow.core.presentation.navigation.transaction


import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value

class TransactionComponent(componentContext: ComponentContext) :
    ComponentContext by componentContext {

    val navigation = StackNavigation<TransactionRoute>()

    val stack: Value<ChildStack<TransactionRoute, TransactionChild>> =
        childStack(
            source = navigation,
            serializer = TransactionRoute.serializer(),
            initialConfiguration = TransactionRoute.Dashboard,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: TransactionRoute, context: ComponentContext): TransactionChild {
        return when (route) {
            TransactionRoute.Dashboard -> TransactionChild.DashBoard(
                TransactionDashBoardScreenComponent(
                    context,
                    onNavigateToIncome = { navigation.pushNew(TransactionRoute.Income) },
                    onNavigateToExpense = { navigation.pushNew(TransactionRoute.Expense) },
                    onBack = {
                        navigation.pop()
                    }
                )
            )

            TransactionRoute.Expense -> TransactionChild.Expense(ExpenseScreenComponent(context) {
                navigation.pop()
            })

            TransactionRoute.Income -> TransactionChild.Income(
                IncomeScreenComponent(context)
                {
                    navigation.pop()
                })
        }
    }


}