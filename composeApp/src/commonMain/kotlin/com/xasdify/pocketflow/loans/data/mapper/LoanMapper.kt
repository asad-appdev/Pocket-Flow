package com.xasdify.pocketflow.loans.data.mapper

import com.xasdify.pocketflow.data.local.entity.LoanEntity
import com.xasdify.pocketflow.data.local.entity.LoanPaymentEntity
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanPayment

fun LoanEntity.toDomain(totalPaid: Double = 0.0): Loan {
    return Loan(
        id = id,
        type = type,
        lenderName = lenderName,
        principalAmount = principalAmount,
        interestRate = interestRate,
        currencyCode = currencyCode,
        startDate = startDate,
        dueDate = dueDate,
        status = status,
        description = description,
        totalPaid = totalPaid,
        remainingBalance = (principalAmount - totalPaid).coerceAtLeast(0.0),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Loan.toEntity(): LoanEntity {
    return LoanEntity(
        id = id,
        type = type,
        lenderName = lenderName,
        principalAmount = principalAmount,
        interestRate = interestRate,
        currencyCode = currencyCode,
        startDate = startDate,
        dueDate = dueDate,
        status = status,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun LoanPaymentEntity.toDomain(): LoanPayment {
    return LoanPayment(
        id = id,
        loanId = loanId,
        amount = amount,
        principalPortion = principalPortion,
        interestPortion = interestPortion,
        paymentDate = paymentDate,
        notes = notes,
        createdAt = createdAt
    )
}

fun LoanPayment.toEntity(): LoanPaymentEntity {
    return LoanPaymentEntity(
        id = id,
        loanId = loanId,
        amount = amount,
        principalPortion = principalPortion,
        interestPortion = interestPortion,
        paymentDate = paymentDate,
        notes = notes,
        createdAt = createdAt
    )
}