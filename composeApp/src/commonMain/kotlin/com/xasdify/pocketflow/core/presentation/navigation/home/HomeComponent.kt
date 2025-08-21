package com.xasdify.pocketflow.core.presentation.navigation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.xasdify.pocketflow.core.presentation.navigation.main.MainRoute

class HomeComponent(componentContext: ComponentContext, val onNavigateTo: (MainRoute) -> Unit) :
    ComponentContext by componentContext {

    val navigation = StackNavigation<HomeRoute>()

    val childStack: Value<ChildStack<HomeRoute, HomeChild>> =
        childStack(
            source = navigation,
            serializer = HomeRoute.serializer(),
            initialConfiguration = HomeRoute.DashBoard,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(route: HomeRoute, context: ComponentContext): HomeChild {
        return when (route) {
            HomeRoute.DashBoard -> HomeChild.DashBoard(
                HomeScreenComponent(
                    context,
                    onNavigateTo = onNavigateTo
                )
            )
        }
    }


}