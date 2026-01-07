package com.xasdify.pocketflow.core.presentation

import androidx.compose.runtime.Composable
import com.xasdify.pocketflow.core.presentation.navigation.root.RootComponent
import com.xasdify.pocketflow.core.presentation.navigation.root.RootContent

import com.xasdify.pocketflow.ui.theme.PocketFlowTheme

@Composable
fun App(root: RootComponent) {
    PocketFlowTheme {
        RootContent(root)
    }
}