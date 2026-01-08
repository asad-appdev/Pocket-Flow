package com.xasdify.pocketflow.loans.domain.model

data class LoanWithPayments(
    val loan: Loan,
    val payments: List<LoanPayment>
) {
    val totalPaidAmount: Double
        get() = payments.sumOf { it.amount }
    
    val totalPrincipalPaid: Double
        get() = payments.sumOf { it.principalPortion }
    
    val totalInterestPaid: Double
        get() = payments.sumOf { it.interestPortion }
}
