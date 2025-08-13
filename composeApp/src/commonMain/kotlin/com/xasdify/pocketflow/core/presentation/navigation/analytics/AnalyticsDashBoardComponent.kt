package com.xasdify.pocketflow.core.presentation.navigation.analytics

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.xasdify.pocketflow.analytics_reporting.presentation.dashboard.AnalyticsDashBoardEvent

class AnalyticsDashBoardComponent(
    context: ComponentContext,
    val onNavigateToReport: (String) -> Unit
) : ComponentContext by context {
    private val text = MutableValue("")
    val txt: Value<String> = text
    fun event(event: AnalyticsDashBoardEvent) {
        when (event) {
            is AnalyticsDashBoardEvent.OnButtonClick -> onNavigateToReport("some text")
        }

    }
}