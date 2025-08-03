package com.xasdify.pocketflow.profile.domain.model

data class UserProfile(
    val name: String,
    val email: String,
    val avatarUri: String?,
    val currency: String
)