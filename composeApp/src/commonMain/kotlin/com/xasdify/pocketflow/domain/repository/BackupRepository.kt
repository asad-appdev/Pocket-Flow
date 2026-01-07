package com.xasdify.pocketflow.domain.repository

import kotlinx.coroutines.flow.Flow

interface BackupRepository {
    fun getLastBackupTime(): Flow<Long>
    suspend fun createLocalBackup(): Boolean
    suspend fun uploadToDrive(): Boolean
    suspend fun restoreFromDrive(): Boolean
    suspend fun scheduleDailyBackup()
}
