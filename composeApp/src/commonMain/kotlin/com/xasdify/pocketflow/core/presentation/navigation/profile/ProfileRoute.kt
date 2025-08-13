package com.xasdify.pocketflow.core.presentation.navigation.profile

import kotlinx.serialization.Serializable

@Serializable
sealed class ProfileRoute() {
    @Serializable
    object UserProfile : ProfileRoute()

    @Serializable
    object Settings : ProfileRoute()

}