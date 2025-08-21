package com.xasdify.pocketflow.core.data.storage

expect class KeyValueStore {
    fun putString(key: String, value: String)
    fun getString(key: String): String?
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun clear()
}