package com.xasdify.pocketflow.core.presentation.navigation.finance

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

class FinanceComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    val navigation = StackNavigation<FinanceRoute>()

    val stack: Value<ChildStack<FinanceRoute, FinanceChild>> =
        childStack(
            source = navigation,
            serializer = FinanceRoute.serializer(),
            handleBackButton = true,
            initialConfiguration = FinanceRoute.Budget,
            childFactory = ::createChild

        )

    private fun createChild(route: FinanceRoute, context: ComponentContext): FinanceChild {

        return when (route) {
            FinanceRoute.Budget -> FinanceChild.Budget(BudgetScreenComponent(context))
            FinanceRoute.Debt -> FinanceChild.Debt(DebtScreenComponent(context))
            FinanceRoute.Goals -> FinanceChild.Goal(GoalScreenComponent(context))
        }
    }

}