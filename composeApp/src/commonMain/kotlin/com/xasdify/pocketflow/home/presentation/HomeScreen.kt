package com.xasdify.pocketflow.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.home.HomeScreenComponent
import com.xasdify.pocketflow.core.presentation.navigation.main.MainRoute

@Composable
fun HomeScreen(component: HomeScreenComponent) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("To Transaction", modifier = Modifier.clickable(onClick = {
                component.onNavigateTo(MainRoute.Transactions)
            }))
            Text("To Finance", modifier = Modifier.clickable(onClick = {
                component.onNavigateTo(MainRoute.Finance)
            }))

            Text("To Analytics", modifier = Modifier.clickable(onClick = {
                component.onNavigateTo(MainRoute.Analytics)
            }))

            Text("To Profile", modifier = Modifier.clickable(onClick = {
                component.onNavigateTo(MainRoute.Profile)
            }))
        }
    }

}