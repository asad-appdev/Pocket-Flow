package com.xasdify.pocketflow.data.repository

import com.xasdify.pocketflow.data.local.dao.PresetDao
import com.xasdify.pocketflow.domain.model.Preset
import com.xasdify.pocketflow.domain.model.toDomain
import com.xasdify.pocketflow.domain.model.toEntity
import com.xasdify.pocketflow.domain.repository.PresetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class PresetRepositoryImpl(
    private val presetDao: PresetDao
) : PresetRepository {

    override fun getAllPresets(): Flow<List<Preset>> {
        return presetDao.getAllPresets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFavoritePresets(): Flow<List<Preset>> {
        return presetDao.getFavoritePresets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPresetById(id: Long): Flow<Preset?> {
        return presetDao.getPresetById(id).map { it?.toDomain() }
    }

    override fun searchPresets(query: String): Flow<List<Preset>> {
        return presetDao.searchPresets(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun savePreset(preset: Preset): Long {
        return presetDao.insertPreset(preset.toEntity())
    }

    override suspend fun deletePreset(preset: Preset) {
        presetDao.deletePreset(preset.toEntity())
    }

    override suspend fun deletePresetById(id: Long) {
        presetDao.deletePresetById(id)
    }

    override suspend fun toggleFavorite(id: Long, isFavorite: Boolean) {
        val entity = presetDao.getPresetById(id).firstOrNull()
        entity?.let {
            presetDao.updatePreset(it.copy(isFavorite = isFavorite))
        }
    }
}
