package com.xasdify.pocketflow.core.presentation.navigation.analytics

import com.arkivanov.decompose.ComponentContext

class AnalyticsReportComponent(
    val text: String,
    context: ComponentContext,
    val onGoBack: () -> Unit
) : ComponentContext by context {
    fun goBack() {
        onGoBack()
    }

}