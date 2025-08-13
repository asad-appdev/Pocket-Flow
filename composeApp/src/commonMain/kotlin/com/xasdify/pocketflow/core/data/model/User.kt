package com.xasdify.pocketflow.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
)
