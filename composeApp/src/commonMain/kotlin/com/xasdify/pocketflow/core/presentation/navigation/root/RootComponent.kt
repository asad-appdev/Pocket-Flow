package com.xasdify.pocketflow.core.presentation.navigation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.xasdify.pocketflow.core.presentation.navigation.main.MainComponent

class RootComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    val navigation = StackNavigation<RootRoute>()

    val childStack: Value<ChildStack<RootRoute, RootChild>> =
        childStack(
            source = navigation,
            serializer = RootRoute.serializer(),
            initialConfiguration = RootRoute.Landing,
            handleBackButton = true,
            childFactory = ::createChild
        )


    private fun createChild(route: RootRoute, context: ComponentContext): RootChild {
        return when (route) {
            RootRoute.Landing -> RootChild.Landing(LandingComponent(context) { isLogin ->
                if (isLogin) {
                    navigation.replaceCurrent(RootRoute.Main)
                } else {
                    navigation.replaceCurrent(RootRoute.Auth)
                }
            })

            RootRoute.Auth -> RootChild.Auth(AuthScreenComponent(context) {
                navigation.replaceCurrent(RootRoute.Main)
            })

            RootRoute.Main -> RootChild.Main(MainComponent(context))
        }


    }

}