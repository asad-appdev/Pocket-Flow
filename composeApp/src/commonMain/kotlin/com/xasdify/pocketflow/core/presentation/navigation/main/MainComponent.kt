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

class MainComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<MainRoute>()

    val childStack: Value<ChildStack<MainRoute, MainChild>> =
        childStack(
            source = navigation,
            serializer = MainRoute.serializer(),
            initialConfiguration = MainRoute.Home,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: MainRoute, context: ComponentContext): MainChild =
        when (route) {
            MainRoute.Home -> MainChild.Home(HomeComponent(context) {
                navigation.pushNew(it, onComplete = {
                    // navigation.replaceCurrent()
                })
            })

            MainRoute.Transactions -> MainChild.Transaction(TransactionComponent(context))
            MainRoute.Finance -> MainChild.Finance(FinanceComponent(context))
            MainRoute.Analytics -> MainChild.Analytics(AnalyticsComponent(context))
            MainRoute.Profile -> MainChild.Profile(ProfileComponent(context))
        }


}