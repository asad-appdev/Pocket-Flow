package com.xasdify.pocketflow.core.presentation.navigation.profile

sealed interface ProfileChild {

    class ProfileScreen(val component: ProfileScreenComponent) : ProfileChild
    class SettingScreen(val component: SettingScreenComponent) : ProfileChild
}