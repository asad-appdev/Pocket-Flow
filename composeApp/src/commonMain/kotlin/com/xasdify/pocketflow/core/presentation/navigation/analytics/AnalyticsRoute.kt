package com.xasdify.pocketflow.core.presentation.navigation.analytics

import kotlinx.serialization.Serializable

@Serializable
sealed class AnalyticsRoute {
    @Serializable
    data object Dashboard : AnalyticsRoute()

    @Serializable
    data class Report(val text: String = "") : AnalyticsRoute()


}