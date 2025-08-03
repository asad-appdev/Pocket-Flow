package com.xasdify.pocketflow.transactions.domain.repository

import com.xasdify.pocketflow.transactions.domain.model.Category

interface CategoryRepository {
    suspend fun getAllCategories(type: String): List<Category>
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(id: Int)
    suspend fun getCategoryById(id: Int): Category?
}