package com.xasdify.pocketflow.data.local.dao

import androidx.room.*
import com.xasdify.pocketflow.data.local.entity.LoanPaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanPaymentDao {
    @Query("SELECT * FROM loan_payments WHERE loanId = :loanId ORDER BY paymentDate DESC")
    fun getPaymentsByLoanId(loanId: Long): Flow<List<LoanPaymentEntity>>
    
    @Query("SELECT * FROM loan_payments WHERE id = :id")
    suspend fun getPaymentById(id: Long): LoanPaymentEntity?
    
    @Insert
    suspend fun insertPayment(payment: LoanPaymentEntity): Long
    
    @Update
    suspend fun updatePayment(payment: LoanPaymentEntity)
    
    @Delete
    suspend fun deletePayment(payment: LoanPaymentEntity)
    
    @Query("SELECT SUM(amount) FROM loan_payments WHERE loanId = :loanId")
    suspend fun getTotalPaidForLoan(loanId: Long): Double?
    
    @Query("SELECT SUM(principalPortion) FROM loan_payments WHERE loanId = :loanId")
    suspend fun getTotalPrincipalPaidForLoan(loanId: Long): Double?
    
    @Query("SELECT SUM(interestPortion) FROM loan_payments WHERE loanId = :loanId")
    suspend fun getTotalInterestPaidForLoan(loanId: Long): Double?
    
    @Query("DELETE FROM loan_payments WHERE loanId = :loanId")
    suspend fun deletePaymentsByLoanId(loanId: Long)
}
