package com.xasdify.pocketflow.core.presentation.navigation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsContent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeContent

@Composable
fun RootContent(root: RootComponent) {
    Children(stack = root.childStack) { child ->
        when (val inst = child.instance) {
            is RootChild.Analytics -> AnalyticsContent(inst.component)
            is RootChild.Finance -> {}
            is RootChild.Home -> {
                HomeContent(inst.component)
            }

            is RootChild.Profile -> {}
            is RootChild.Transaction -> {}
        }
    }
}
