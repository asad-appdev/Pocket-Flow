package com.xasdify.pocketflow.analytics_reporting.presentation.dashboard


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsDashBoardComponent

@Composable
fun AnalyticsDashboardScreen(component: AnalyticsDashBoardComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("AnalyticsDashboardScreen", modifier = Modifier.clickable(onClick = {
            component.event(AnalyticsDashBoardEvent.OnButtonClick)
        }))
    }
}
