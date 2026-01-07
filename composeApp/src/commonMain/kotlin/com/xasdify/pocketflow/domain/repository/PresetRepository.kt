package com.xasdify.pocketflow.domain.repository

import com.xasdify.pocketflow.domain.model.Preset
import kotlinx.coroutines.flow.Flow

interface PresetRepository {
    fun getAllPresets(): Flow<List<Preset>>
    fun getFavoritePresets(): Flow<List<Preset>>
    fun getPresetById(id: Long): Flow<Preset?>
    fun searchPresets(query: String): Flow<List<Preset>>
    suspend fun savePreset(preset: Preset): Long
    suspend fun deletePreset(preset: Preset)
    suspend fun deletePresetById(id: Long)
    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)
}
