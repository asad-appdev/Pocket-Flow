package com.xasdify.pocketflow.core.presentation.navigation.root

import kotlinx.serialization.Serializable

@Serializable
sealed class RootRoute {

    @Serializable
    object Landing : RootRoute()

    @Serializable
    object Auth : RootRoute()

    @Serializable
    object Main : RootRoute()
}