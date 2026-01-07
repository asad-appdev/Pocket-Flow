package com.xasdify.pocketflow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xasdify.pocketflow.data.local.dao.BackupDao
import com.xasdify.pocketflow.data.local.dao.PresetDao
import com.xasdify.pocketflow.data.local.dao.TagDao
import com.xasdify.pocketflow.data.local.entity.BackupMetadataEntity
import com.xasdify.pocketflow.data.local.entity.PresetEntity
import com.xasdify.pocketflow.data.local.entity.PresetTagCrossRef
import com.xasdify.pocketflow.data.local.entity.TagEntity

@Database(
    entities = [
        PresetEntity::class,
        TagEntity::class,
        PresetTagCrossRef::class,
        BackupMetadataEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun presetDao(): PresetDao
    abstract fun tagDao(): TagDao
    abstract fun backupDao(): BackupDao

    companion object {
        const val DB_NAME = "pocketflow.db"
    }
}
