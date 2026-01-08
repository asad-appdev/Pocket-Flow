package com.xasdify.pocketflow.core.presentation.navigation.finance

import com.arkivanov.decompose.ComponentContext

class DebtScreenComponent(
    val context: ComponentContext,
    val onAddLoan: () -> Unit,
    val onLoanClicked: (Long) -> Unit = {}
) : ComponentContext by context {
    fun onAddLoanClicked() = onAddLoan()
    fun onLoanItemClicked(id: Long) = onLoanClicked(id)
}