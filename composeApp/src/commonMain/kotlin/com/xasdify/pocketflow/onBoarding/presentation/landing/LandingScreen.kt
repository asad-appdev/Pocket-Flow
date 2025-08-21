package com.xasdify.pocketflow.onBoarding.presentation.landing


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.profile.SettingScreenComponent
import com.xasdify.pocketflow.core.presentation.navigation.root.LandingComponent

@Composable
fun LandingScreen(component: LandingComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Splash Screen")

        }
    }
}