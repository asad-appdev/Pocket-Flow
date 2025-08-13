package com.xasdify.pocketflow.core.presentation

import androidx.compose.runtime.Composable
import com.xasdify.pocketflow.core.presentation.navigation.root.RootComponent
import com.xasdify.pocketflow.core.presentation.navigation.root.RootContent
import com.xasdify.pocketflow.core.presentation.theme.AppTheme

@Composable

fun App(root: RootComponent) {
    // val root = rememberRootComponent(::RootComponent)
    AppTheme {
        RootContent(root)
        // MainNavigationComponent(root)
    }

}

