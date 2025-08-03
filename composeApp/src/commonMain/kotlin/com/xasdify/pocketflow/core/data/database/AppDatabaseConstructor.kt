package com.xasdify.pocketflow.core.data.database

import androidx.room.RoomDatabaseConstructor


expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}