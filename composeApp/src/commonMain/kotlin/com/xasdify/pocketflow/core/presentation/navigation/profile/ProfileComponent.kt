package com.xasdify.pocketflow.core.presentation.navigation.profile

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value

class ProfileComponent(componentContext: ComponentContext) : ComponentContext by componentContext {

    val navigation = StackNavigation<ProfileRoute>()
    val childStack: Value<ChildStack<ProfileRoute, ProfileChild>> =
        childStack(
            source = navigation,
            serializer = ProfileRoute.serializer(),
            initialConfiguration = ProfileRoute.UserProfile,
            handleBackButton = true,
            childFactory = ::createChild
        )


    private fun createChild(route: ProfileRoute, context: ComponentContext): ProfileChild {
        return when (route) {
            ProfileRoute.Settings -> ProfileChild.SettingScreen(SettingScreenComponent(context = context) {
                navigation.pop()
            })

            ProfileRoute.UserProfile -> ProfileChild.ProfileScreen(ProfileScreenComponent(context = context) {
                navigation.pushNew(ProfileRoute.Settings)
            })
        }
    }

}