package com.xasdify.pocketflow.loans.data.repository

import com.xasdify.pocketflow.data.local.dao.LoanDao
import com.xasdify.pocketflow.data.local.dao.LoanPaymentDao
import com.xasdify.pocketflow.loans.data.mapper.toDomain
import com.xasdify.pocketflow.loans.data.mapper.toEntity
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanPayment
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.loans.domain.model.LoanWithPayments
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class LoanRepository(
    private val loanDao: LoanDao,
    private val loanPaymentDao: LoanPaymentDao
) {
    fun getAllLoans(): Flow<List<Loan>> {
        return loanDao.getAllLoans().map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    fun getLoansByType(type: LoanType): Flow<List<Loan>> {
        return loanDao.getLoansByType(type.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    fun getLoansByStatus(status: LoanStatus): Flow<List<Loan>> {
        return loanDao.getLoansByStatus(status.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    fun getLoansByTypeAndStatus(type: LoanType, status: LoanStatus): Flow<List<Loan>> {
        return loanDao.getLoansByTypeAndStatus(type.name, status.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    suspend fun getLoanById(id: Long): Loan? {
        val entity = loanDao.getLoanById(id) ?: return null
        val totalPaid = loanPaymentDao.getTotalPaidForLoan(id) ?: 0.0
        return entity.toDomain(totalPaid)
    }

    fun getLoanWithPayments(loanId: Long): Flow<LoanWithPayments?> {
        return combine(
            loanDao.getLoanByIdFlow(loanId),
            loanPaymentDao.getPaymentsByLoanId(loanId)
        ) { loanEntity, paymentEntities ->
            loanEntity?.let { entity ->
                val totalPaid = paymentEntities.sumOf { it.amount }
                val loan = entity.toDomain(totalPaid)
                val payments = paymentEntities.map { it.toDomain() }
                LoanWithPayments(loan, payments)
            }
        }
    }

    suspend fun insertLoan(loan: Loan): Long {
        return loanDao.insertLoan(loan.toEntity())
    }

    suspend fun updateLoan(loan: Loan) {
        loanDao.updateLoan(loan.toEntity())
    }

    suspend fun deleteLoan(loanId: Long) {
        loanDao.deleteLoanById(loanId)
    }

    suspend fun addPayment(payment: LoanPayment): Long {
        val paymentId = loanPaymentDao.insertPayment(payment.toEntity())

        // Check if loan should be auto-closed
        val loan = getLoanById(payment.loanId)
        loan?.let {
            if (it.remainingBalance <= 0 && it.status == LoanStatus.ACTIVE) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.CLOSED,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }

        return paymentId
    }

    suspend fun closeLoan(loanId: Long) {
        val loan = getLoanById(loanId)
        loan?.let {
            if (it.status == LoanStatus.ACTIVE) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.CLOSED,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }
    }

    suspend fun reopenLoan(loanId: Long) {
        val loan = getLoanById(loanId)
        loan?.let {
            if (it.status == LoanStatus.CLOSED) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.ACTIVE,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }
    }

    fun getPaymentsByLoanId(loanId: Long): Flow<List<LoanPayment>> {
        return loanPaymentDao.getPaymentsByLoanId(loanId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun deletePayment(paymentId: Long) {
        val payment = loanPaymentDao.getPaymentById(paymentId)
        payment?.let {
            loanPaymentDao.deletePayment(it)

            // Reopen loan if it was closed and now has remaining balance
            val loan = getLoanById(it.loanId)
            loan?.let { l ->
                if (l.status == LoanStatus.CLOSED && l.remainingBalance > 0) {
                    reopenLoan(l.id)
                }
            }
        }
    }

    // Summary statistics
    suspend fun getTotalLoansByType(type: LoanType): Int {
        return loanDao.getLoansByType(type.name).map { it.size }.hashCode() // Simplified
    }

    suspend fun getTotalActiveLoanAmount(type: LoanType): Double {
        // This would need a custom query in a real implementation
        return 0.0 // Placeholder
    }
}