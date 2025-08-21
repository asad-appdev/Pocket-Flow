package com.xasdify.pocketflow.core.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

actual class KeyValueStore(private val context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    actual fun putString(key: String, value: String) {
        prefs.edit { putString(key, value) }
    }

    actual fun getString(key: String): String? =
        prefs.getString(key, null)

    actual fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    actual fun getBoolean(key: String, default: Boolean): Boolean =
        prefs.getBoolean(key, default)

    actual fun clear() {
        prefs.edit { clear() }
    }
}
