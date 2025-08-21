package com.xasdify.pocketflow.core.data.storage

import platform.Foundation.NSUserDefaults

actual class KeyValueStore {

    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()

    actual fun putString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
    }

    actual fun getString(key: String): String? =
        defaults.stringForKey(key)

    actual fun putBoolean(key: String, value: Boolean) {
        defaults.setBool(value, forKey = key)
    }

    actual fun getBoolean(key: String, default: Boolean): Boolean =
        if (defaults.objectForKey(key) != null) defaults.boolForKey(key) else default

    actual fun clear() {
        defaults.dictionaryRepresentation().keys.forEach {
            defaults.removeObjectForKey(it as String)
        }
    }
}
