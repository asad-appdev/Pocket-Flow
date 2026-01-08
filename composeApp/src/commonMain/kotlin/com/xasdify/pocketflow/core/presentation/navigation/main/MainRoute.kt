package com.xasdify.pocketflow.core.presentation.navigation.main

import kotlinx.serialization.Serializable

@Serializable
sealed class MainRoute {
    @Serializable
    object Home : MainRoute()

    @Serializable
    object Analytics : MainRoute()

    @Serializable
    object Finance : MainRoute()



    @Serializable
    object Transactions : MainRoute()

    @Serializable
    object Profile : MainRoute()
    
    @Serializable
    object Budget : MainRoute()
    
    @Serializable
    object Loans : MainRoute()
    
    @Serializable
    object AddBudget : MainRoute()
    
    @Serializable
    object AddLoan : MainRoute()
    
    @Serializable
    data class LoanDetail(val loanId: Long) : MainRoute()
}

