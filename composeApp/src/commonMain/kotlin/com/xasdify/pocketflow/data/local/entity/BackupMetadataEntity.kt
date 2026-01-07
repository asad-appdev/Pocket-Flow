package com.xasdify.pocketflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity(tableName = "backup_metadata")
data class BackupMetadataEntity(
    @PrimaryKey val id: Int = 1, // Singleton row
    val lastBackupTime: Long = 0,
    val driveFileId: String? = null,
    val deviceId: String? = null,
    val backupVersion: Int = 0
)
