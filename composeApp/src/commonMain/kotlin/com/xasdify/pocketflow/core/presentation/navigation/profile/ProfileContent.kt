package com.xasdify.pocketflow.core.presentation.navigation.profile

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.profile.presentation.settings.SettingsScreen
import com.xasdify.pocketflow.profile.presentation.userProfile.ProfileScreen

@Composable
fun ProfileContent(component: ProfileComponent) {
    Children(stack = component.childStack) { child ->
        when (val c = child.instance) {
            is ProfileChild.ProfileScreen -> ProfileScreen(c.component)
            is ProfileChild.SettingScreen -> SettingsScreen(c.component)
        }

    }
}