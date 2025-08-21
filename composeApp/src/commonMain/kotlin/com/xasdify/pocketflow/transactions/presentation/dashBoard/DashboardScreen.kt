package com.xasdify.pocketflow.transactions.presentation.dashBoard


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import com.xasdify.pocketflow.core.presentation.navigation.transaction.TransactionDashBoardScreenComponent

@Composable
fun DashboardScreen(component: TransactionDashBoardScreenComponent) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("Transaction Dashboard Screen")
            Text("To Expense", modifier = Modifier.clickable(onClick = {
                component.onNavigateToExpense()
            }))
            Text("To Income", modifier = Modifier.clickable(onClick = {
                component.onNavigateToIncome()
            }))
        }
    }
}