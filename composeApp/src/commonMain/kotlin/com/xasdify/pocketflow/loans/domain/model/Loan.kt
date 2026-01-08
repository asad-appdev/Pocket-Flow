package com.xasdify.pocketflow.loans.domain.model

import com.xasdify.pocketflow.utils.getCurrentTimeMilli

enum class LoanType {
    TAKEN,  // User borrowed money (owes someone)
    GIVEN   // User lent money (someone owes user)
}

enum class LoanStatus {
    ACTIVE,
    CLOSED
}

data class Loan(
    val id: Long = 0,
    val type: LoanType,
    val lenderName: String, // For TAKEN: who lent to you; For GIVEN: who you lent to
    val principalAmount: Double,
    val interestRate: Double, // Annual percentage rate
    val currencyCode: String,
    val startDate: Long,
    val dueDate: Long?,
    val status: LoanStatus,
    val description: String?,
    val totalPaid: Double = 0.0, // Calculated from payments
    val remainingBalance: Double, // principalAmount - totalPaid
    val createdAt: Long,
    val updatedAt: Long
) {
    val isOverdue: Boolean
        get() = dueDate?.let { it < getCurrentTimeMilli() && status == LoanStatus.ACTIVE } ?: false

    val progressPercentage: Float
        get() = if (principalAmount > 0) (totalPaid / principalAmount).coerceIn(0.0, 1.0)
            .toFloat() else 0f
}