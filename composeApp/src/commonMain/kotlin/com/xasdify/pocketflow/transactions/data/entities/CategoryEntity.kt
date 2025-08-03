package com.xasdify.pocketflow.transactions.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,                         // e.g., "Salary", "Freelance"
    val icon: String? = null,
    val colorHex: String? = null,
    val type: String? = null
)