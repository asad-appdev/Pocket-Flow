package com.xasdify.pocketflow.account.domain.repository

import com.xasdify.pocketflow.account.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAllAccounts(): Flow<List<Account>>
    fun getAccountById(id: Long): Flow<Account?>
    suspend fun addAccount(account: Account): Long
    suspend fun updateAccount(account: Account)
    suspend fun deleteAccount(accountId: Long)
    // suspend fun updateAccountBalance(accountId: Long, newBalance: Double) // Balances are usually calculated from transactions
}