package com.xasdify.pocketflow.loans.domain.model

data class LoanPayment(
    val id: Long = 0,
    val loanId: Long,
    val amount: Double,
    val principalPortion: Double,
    val interestPortion: Double,
    val paymentDate: Long,
    val notes: String?,
    val createdAt: Long
)
