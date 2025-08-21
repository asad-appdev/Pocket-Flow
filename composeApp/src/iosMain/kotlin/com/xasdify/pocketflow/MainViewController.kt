package com.xasdify.pocketflow

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.xasdify.pocketflow.core.di.initKoin
import com.xasdify.pocketflow.core.presentation.App
import com.xasdify.pocketflow.core.presentation.navigation.root.RootComponent

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {

    val root = remember {
        RootComponent(
            DefaultComponentContext(
                LifecycleRegistry()
            )
        )
    }
    App(root)
}