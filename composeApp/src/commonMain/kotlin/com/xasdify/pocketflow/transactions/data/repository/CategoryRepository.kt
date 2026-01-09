package com.xasdify.pocketflow.transactions.data.repository

import com.xasdify.pocketflow.transactions.data.dao.CategoryDao
import com.xasdify.pocketflow.transactions.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    
    suspend fun getCategoryById(id: Int): CategoryEntity? = categoryDao.getCategoryById(id)
    
    fun getCategoriesByType(type: String): Flow<List<CategoryEntity>> = 
        categoryDao.getCategoriesByType(type)
    
    suspend fun insertCategory(category: CategoryEntity): Long = categoryDao.insertCategory(category)
    
    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)
    
    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)
    
    suspend fun deleteCategoryById(id: Int) = categoryDao.deleteCategoryById(id)
}