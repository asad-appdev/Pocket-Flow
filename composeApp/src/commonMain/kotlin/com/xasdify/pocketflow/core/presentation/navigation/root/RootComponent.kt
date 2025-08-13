package com.xasdify.pocketflow.core.presentation.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsComponent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceComponent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeComponent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileComponent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<RootRoute>()

    val childStack: Value<ChildStack<RootRoute, RootChild>> =
        childStack(
            source = navigation,
            // add this if your RootRoute is @Serializable
            serializer = RootRoute.serializer(),
            initialConfiguration = RootRoute.Home,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: RootRoute, context: ComponentContext): RootChild =
        when (route) {
            RootRoute.Home -> RootChild.Home(HomeComponent(context))
            RootRoute.Transactions -> RootChild.Transaction(TransactionComponent(context))
            RootRoute.Finance -> RootChild.Finance(FinanceComponent(context))
            RootRoute.Analytics -> RootChild.Analytics(AnalyticsComponent(context))
            RootRoute.Profile -> RootChild.Profile(ProfileComponent(context))
        }


}