package com.xasdify.pocketflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xasdify.pocketflow.data.local.entity.BackupMetadataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackupDao {
    @Query("SELECT * FROM backup_metadata WHERE id = 1")
    fun getMetadata(): Flow<BackupMetadataEntity?>

    @Query("SELECT * FROM backup_metadata WHERE id = 1")
    suspend fun getMetadataOneShot(): BackupMetadataEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMetadata(metadata: BackupMetadataEntity)
}
