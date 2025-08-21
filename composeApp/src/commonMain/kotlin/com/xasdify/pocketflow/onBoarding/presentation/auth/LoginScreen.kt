package com.xasdify.pocketflow.onBoarding.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.root.AuthScreenComponent

@Composable
fun LoginScreen(component: AuthScreenComponent) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("P")
            Text("Login", modifier = Modifier.clickable(onClick = {
                component.onLogin()
            }))

        }
    }
}