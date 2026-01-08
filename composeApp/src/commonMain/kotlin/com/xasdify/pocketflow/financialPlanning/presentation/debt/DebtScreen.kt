package com.xasdify.pocketflow.financialPlanning.presentation.debt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.xasdify.pocketflow.core.presentation.navigation.finance.DebtScreenComponent
import com.xasdify.pocketflow.loans.presentation.list.LoansListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtScreen(component: DebtScreenComponent) {
    // Provide navigation actions
    // For now, we use placeholders or simple navigation if available
    val onNavigateBack = { /* TODO: Navigate back if needed */ }
    val onNavigateToAddLoan = {
        component.onAddLoanClicked()
    }

    LoansListScreen(
        onNavigateBack = onNavigateBack,
        onNavigateToAddLoan = onNavigateToAddLoan
    )
}