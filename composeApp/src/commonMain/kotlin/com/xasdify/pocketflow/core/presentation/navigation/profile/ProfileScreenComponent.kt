package com.xasdify.pocketflow.core.presentation.navigation.profile

import com.arkivanov.decompose.ComponentContext

class ProfileScreenComponent(
    val context: ComponentContext,
    val onNavigateToSettings: () -> Unit
) : ComponentContext by context {

}