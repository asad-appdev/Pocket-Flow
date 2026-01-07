package com.xasdify.pocketflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "presets")
data class PresetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val p1x: Float,
    val p1y: Float,
    val p2x: Float,
    val p2y: Float,
    val durationMs: Long = 300,
    val note: String = "",
    val isFavorite: Boolean = false,
    val createdAt: Long = 0L,
    val modifiedAt: Long = 0L
)
