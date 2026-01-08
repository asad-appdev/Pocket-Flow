package com.xasdify.pocketflow.loans.domain


import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanPayment
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.loans.domain.model.LoanWithPayments
import kotlinx.coroutines.flow.Flow

interface LoanRepository {
    fun getAllLoans(): Flow<List<Loan>>

    fun getLoansByType(type: LoanType): Flow<List<Loan>>

    fun getLoansByStatus(status: LoanStatus): Flow<List<Loan>>

    fun getLoansByTypeAndStatus(type: LoanType, status: LoanStatus): Flow<List<Loan>>

    suspend fun getLoanById(id: Long): Loan?

    fun getLoanWithPayments(loanId: Long): Flow<LoanWithPayments?>

    suspend fun insertLoan(loan: Loan): Long

    suspend fun updateLoan(loan: Loan)

    suspend fun deleteLoan(loanId: Long)

    suspend fun addPayment(payment: LoanPayment): Long

    suspend fun closeLoan(loanId: Long)

    suspend fun reopenLoan(loanId: Long)

    fun getPaymentsByLoanId(loanId: Long): Flow<List<LoanPayment>>

    suspend fun deletePayment(paymentId: Long)

    // Summary statistics
    suspend fun getTotalLoansByType(type: LoanType): Int

    suspend fun getTotalActiveLoanAmount(type: LoanType): Double
}