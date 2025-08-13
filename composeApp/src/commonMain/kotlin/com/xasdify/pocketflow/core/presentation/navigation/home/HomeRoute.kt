package com.xasdify.pocketflow.core.presentation.navigation.home

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeRoute {
    @Serializable
    object DashBoard : HomeRoute()
}