package com.xasdify.pocketflow.transactions.domain.mapper

import com.xasdify.pocketflow.transactions.data.entities.CategoryEntity
import com.xasdify.pocketflow.transactions.domain.model.Category


fun CategoryEntity.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        icon = icon,
        colorHex = colorHex
    )
}

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        icon = icon,
        colorHex = colorHex
    )
}