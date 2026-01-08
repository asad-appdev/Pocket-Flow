package com.xasdify.pocketflow.loans.data.repository

import com.xasdify.pocketflow.data.local.dao.LoanDao
import com.xasdify.pocketflow.data.local.dao.LoanPaymentDao
import com.xasdify.pocketflow.loans.data.mapper.toDomain
import com.xasdify.pocketflow.loans.data.mapper.toEntity
import com.xasdify.pocketflow.loans.domain.LoanRepository
import com.xasdify.pocketflow.loans.domain.model.Loan
import com.xasdify.pocketflow.loans.domain.model.LoanPayment
import com.xasdify.pocketflow.loans.domain.model.LoanStatus
import com.xasdify.pocketflow.loans.domain.model.LoanType
import com.xasdify.pocketflow.loans.domain.model.LoanWithPayments
import com.xasdify.pocketflow.utils.getCurrentTimeMilli
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class LoanRepositoryImpl(
    private val loanDao: LoanDao,
    private val loanPaymentDao: LoanPaymentDao
) : LoanRepository {
    override fun getAllLoans(): Flow<List<Loan>> {
        return loanDao.getAllLoans().map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    override fun getLoansByType(type: LoanType): Flow<List<Loan>> {
        return loanDao.getLoansByType(type.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    override fun getLoansByStatus(status: LoanStatus): Flow<List<Loan>> {
        return loanDao.getLoansByStatus(status.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    override fun getLoansByTypeAndStatus(type: LoanType, status: LoanStatus): Flow<List<Loan>> {
        return loanDao.getLoansByTypeAndStatus(type.name, status.name).map { entities ->
            entities.map { entity ->
                val totalPaid = loanPaymentDao.getTotalPaidForLoan(entity.id) ?: 0.0
                entity.toDomain(totalPaid)
            }
        }
    }

    override suspend fun getLoanById(id: Long): Loan? {
        val entity = loanDao.getLoanById(id) ?: return null
        val totalPaid = loanPaymentDao.getTotalPaidForLoan(id) ?: 0.0
        return entity.toDomain(totalPaid)
    }

    override fun getLoanWithPayments(loanId: Long): Flow<LoanWithPayments?> {
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

    override suspend fun insertLoan(loan: Loan): Long {
        return loanDao.insertLoan(loan.toEntity())
    }

    override suspend fun updateLoan(loan: Loan) {
        loanDao.updateLoan(loan.toEntity())
    }

    override suspend fun deleteLoan(loanId: Long) {
        loanDao.deleteLoanById(loanId)
    }

    override suspend fun addPayment(payment: LoanPayment): Long {
        val paymentId = loanPaymentDao.insertPayment(payment.toEntity())

        // Check if loan should be auto-closed
        val loan = getLoanById(payment.loanId)
        loan?.let {
            if (it.remainingBalance <= 0 && it.status == LoanStatus.ACTIVE.name) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.CLOSED.name,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }

        return paymentId
    }

    override suspend fun closeLoan(loanId: Long) {
        val loan = getLoanById(loanId)
        loan?.let {
            if (it.status == LoanStatus.ACTIVE.name) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.CLOSED.name,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }
    }

    override suspend fun reopenLoan(loanId: Long) {
        val loan = getLoanById(loanId)
        loan?.let {
            if (it.status == LoanStatus.CLOSED.name) {
                updateLoan(
                    it.copy(
                        status = LoanStatus.ACTIVE.name,
                        updatedAt = getCurrentTimeMilli()
                    )
                )
            }
        }
    }

    override fun getPaymentsByLoanId(loanId: Long): Flow<List<LoanPayment>> {
        return loanPaymentDao.getPaymentsByLoanId(loanId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deletePayment(paymentId: Long) {
        val payment = loanPaymentDao.getPaymentById(paymentId)
        payment?.let {
            loanPaymentDao.deletePayment(it)

            // Reopen loan if it was closed and now has remaining balance
            val loan = getLoanById(it.loanId)
            loan?.let { l ->
                if (l.status == LoanStatus.CLOSED.name && l.remainingBalance > 0) {
                    reopenLoan(l.id)
                }
            }
        }
    }

    // Summary statistics
    override suspend fun getTotalLoansByType(type: LoanType): Int {
        return loanDao.getLoansByType(type.name).map { it.size }.hashCode() // Simplified
    }

    override suspend fun getTotalActiveLoanAmount(type: LoanType): Double {
        // This would need a custom query in a real implementation
        return 0.0 // Placeholder
    }
}