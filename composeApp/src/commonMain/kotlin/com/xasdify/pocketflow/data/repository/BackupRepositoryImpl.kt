package com.xasdify.pocketflow.data.repository

import com.xasdify.pocketflow.data.local.dao.BackupDao
import com.xasdify.pocketflow.data.local.entity.BackupMetadataEntity
import com.xasdify.pocketflow.domain.repository.BackupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class BackupRepositoryImpl(
    private val backupDao: BackupDao,
    // Inject platform specific file helper
) : BackupRepository {

    override fun getLastBackupTime(): Flow<Long> {
        return backupDao.getMetadata().map { it?.lastBackupTime ?: 0L }
    }

    override suspend fun createLocalBackup(): Boolean {
        // Logic to zip DB files
        // Platform specific implementation needed to access DB file path
        return true
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun uploadToDrive(): Boolean {
        // Logic to authorize and upload
        val now = Clock.System.now().toEpochMilliseconds()

        backupDao.setMetadata(
            backupDao.getMetadataOneShot()?.copy(lastBackupTime = now)
                ?: BackupMetadataEntity(lastBackupTime = now)
        )
        return true
    }

    override suspend fun restoreFromDrive(): Boolean {
        return true
    }

    override suspend fun scheduleDailyBackup() {
        // Platform specific scheduling (WorkManager on Android)
        // Handled via expect/actual or DI injection of a Scheduler
    }
}