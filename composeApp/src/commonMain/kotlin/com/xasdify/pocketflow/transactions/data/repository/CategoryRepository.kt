package com.xasdify.pocketflow.transactions.data.repository

import com.xasdify.pocketflow.transactions.data.dao.CategoryDao
import com.xasdify.pocketflow.transactions.domain.mapper.toDomain
import com.xasdify.pocketflow.transactions.domain.mapper.toEntity
import com.xasdify.pocketflow.transactions.domain.model.Category
import com.xasdify.pocketflow.transactions.domain.repository.CategoryRepository


class CategoryRepositoryImpl(
    private val dao: CategoryDao
) : CategoryRepository {

    override suspend fun getAllCategories(type: String): List<Category> {
        return dao.getCategoriesByType(type).map { it.toDomain() }
    }

    override suspend fun insertCategory(category: Category) {
        dao.insertCategory(category.toEntity())
    }

    override suspend fun deleteCategory(id: Int) {
        dao.deleteCategoryById(id)
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return dao.getCategoryById(id)?.toDomain()
    }
}