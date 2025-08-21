package com.xasdify.pocketflow.core.data.repository

import com.xasdify.pocketflow.core.data.storage.KeyValueStore
import com.xasdify.pocketflow.core.domain.StorageRepository

class StorageRepositoryImpl(val storage: KeyValueStore) : StorageRepository {
    override suspend fun saveString(key: String, value: String) {
        storage.putString("dsd", "dd")
    }

    override suspend fun getString(key: String): String {
        return ""
    }


}