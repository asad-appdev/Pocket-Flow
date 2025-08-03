package com.xasdify.pocketflow.profile.domain.model

data class AppSettings(
    val currency: String = "USD",
    val notificationsEnabled: Boolean = true,
    val biometricEnabled: Boolean = false
)