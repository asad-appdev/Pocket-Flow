package com.xasdify.pocketflow.core.data.database

import androidx.room.RoomDatabase
import com.xasdify.pocketflow.data.local.AppDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<AppDatabase>
}