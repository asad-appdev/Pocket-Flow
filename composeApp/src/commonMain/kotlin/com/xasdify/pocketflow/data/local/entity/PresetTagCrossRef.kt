package com.xasdify.pocketflow.data.local.entity

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "preset_tag_cross_ref",
    primaryKeys = ["presetId", "tagId"],
    indices = [Index("presetId"), Index("tagId")]
)
data class PresetTagCrossRef(
    val presetId: Long,
    val tagId: Long
)
