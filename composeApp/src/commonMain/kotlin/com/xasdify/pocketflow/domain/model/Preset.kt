package com.xasdify.pocketflow.domain.model

import com.xasdify.pocketflow.data.local.entity.PresetEntity
import kotlin.time.ExperimentalTime
import  kotlin.time.Instant

data class Preset @OptIn(ExperimentalTime::class) constructor(
    val id: Long = 0,
    val name: String,
    val p1x: Float,
    val p1y: Float,
    val p2x: Float,
    val p2y: Float,
    val durationMs: Long,
    val note: String,
    val isFavorite: Boolean,
    val createdAt: Instant,
    val modifiedAt: Instant
)

@OptIn(ExperimentalTime::class)
fun PresetEntity.toDomain(): Preset {
    return Preset(
        id = id,
        name = name,
        p1x = p1x,
        p1y = p1y,
        p2x = p2x,
        p2y = p2y,
        durationMs = durationMs,
        note = note,
        isFavorite = isFavorite,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        modifiedAt = Instant.fromEpochMilliseconds(modifiedAt)
    )
}

@OptIn(ExperimentalTime::class)
fun Preset.toEntity(): PresetEntity {
    return PresetEntity(
        id = id,
        name = name,
        p1x = p1x,
        p1y = p1y,
        p2x = p2x,
        p2y = p2y,
        durationMs = durationMs,
        note = note,
        isFavorite = isFavorite,
        createdAt = createdAt.toEpochMilliseconds(),
        modifiedAt = modifiedAt.toEpochMilliseconds()
    )
}