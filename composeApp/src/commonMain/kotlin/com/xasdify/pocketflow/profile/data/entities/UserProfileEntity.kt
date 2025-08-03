package com.xasdify.pocketflow.profile.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val email: String,
    val avatarUri: String?,
    val currency: String
)