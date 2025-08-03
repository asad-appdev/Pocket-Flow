package com.xasdify.pocketflow.transactions.domain.model

data class Category(
    val id: Int,
    val name: String,
    val icon: String? = null,
    val colorHex: String? = null
)


