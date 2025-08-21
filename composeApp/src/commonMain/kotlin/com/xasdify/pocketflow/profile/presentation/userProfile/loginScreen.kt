package com.xasdify.pocketflow.profile.presentation.userProfile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.profile.ProfileScreenComponent

@Composable
fun ProfileScreen(component: ProfileScreenComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Profile Screen")
            Text("To setting", modifier = Modifier.clickable(onClick = {
                component.onNavigateToSettings()
            }))

        }
    }
}