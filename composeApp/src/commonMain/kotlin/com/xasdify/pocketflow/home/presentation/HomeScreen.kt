package com.xasdify.pocketflow.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeScreenComponent

@Composable
fun HomeScreen(component: HomeScreenComponent) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Home Screen 7")
    }

}