package com.xasdify.pocketflow.core.presentation.navigation.analytics


sealed interface AnalyticsChild {
    class Dashboard(val component: AnalyticsDashBoardComponent) : AnalyticsChild
    class Report(val component: AnalyticsReportComponent) : AnalyticsChild
}