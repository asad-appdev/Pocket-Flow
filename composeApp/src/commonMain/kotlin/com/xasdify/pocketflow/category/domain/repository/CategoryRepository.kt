package com.xasdify.pocketflow.category.domain.repository

import com.xasdify.pocketflow.category.domain.model.Category
import com.xasdify.pocketflow.transactions.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoriesByType(type: TransactionType): Flow<List<Category>>
    fun getCategoryById(id: Long): Flow<Category?>
    suspend fun addCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(categoryId: Long)
}