package com.xasdify.pocketflow.core.presentation.navigation.root

import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute {
    @Serializable
    object Home : RootRoute()

    @Serializable
    object Analytics : RootRoute()

    @Serializable
    object Finance : RootRoute()

    @Serializable
    object Transactions : RootRoute()

    @Serializable
    object Profile : RootRoute()
}

