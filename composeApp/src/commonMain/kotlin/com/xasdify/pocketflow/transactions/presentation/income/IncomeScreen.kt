package com.xasdify.pocketflow.transactions.presentation.income

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.xasdify.pocketflow.core.presentation.navigation.transaction.IncomeScreenComponent

@Composable
fun IncomeScreen(component: IncomeScreenComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Income Screen")
            Text("On Back", modifier = Modifier.clickable(onClick = {
                component.onBack()
            }))
        }
    }
}