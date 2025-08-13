package com.xasdify.pocketflow.analytics_reporting.presentation.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.analytics.AnalyticsReportComponent

@Composable
fun ReportsScreen(text: String, component: AnalyticsReportComponent) {

    //val text by component.text.subscribeAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("ReportsScreen $text", modifier = Modifier.clickable(onClick = {
            component.goBack()
        }))
    }
}
