package com.xasdify.pocketflow.core.domain

interface StorageRepository {

    suspend fun saveString(key: String, value: String)
    suspend fun getString(key: String): String
}