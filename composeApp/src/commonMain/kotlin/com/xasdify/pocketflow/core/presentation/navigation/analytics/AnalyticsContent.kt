package com.xasdify.pocketflow.core.presentation.navigation.analytics

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.xasdify.pocketflow.analytics_reporting.presentation.dashboard.AnalyticsDashboardScreen
import com.xasdify.pocketflow.analytics_reporting.presentation.reports.ReportsScreen


@Composable
fun AnalyticsContent(component: AnalyticsComponent) {
    Children(stack = component.childStack) { child ->
        when (val c = child.instance) {
            is AnalyticsChild.Dashboard -> AnalyticsDashboardScreen(c.component)
            is AnalyticsChild.Report -> ReportsScreen(text = c.component.text, c.component)
        }
    }
}