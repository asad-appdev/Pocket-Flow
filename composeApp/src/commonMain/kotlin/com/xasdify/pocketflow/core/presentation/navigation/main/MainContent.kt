package com.xasdify.pocketflow.core.presentation.navigation.main

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsContent
import com.xasdify.pocketflow.core.presentation.navigation.finance.FinanceContent
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeContent
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileContent
import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionContent

@Composable
fun MainContent(root: MainComponent) {
    Children(stack = root.childStack) { child ->
        when (val inst = child.instance) {
            is MainChild.Analytics -> AnalyticsContent(inst.component)
            is MainChild.Finance -> FinanceContent(inst.component)
            is MainChild.Home -> HomeContent(inst.component)
            is MainChild.Profile -> ProfileContent(inst.component)
            is MainChild.Transaction -> TransactionContent(inst.component)
        }
    }
}
