package com.xasdify.pocketflow.analytics_reporting.presentation.dashboard

sealed interface AnalyticsDashBoardEvent {
    data object OnButtonClick : AnalyticsDashBoardEvent
}