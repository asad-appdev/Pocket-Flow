package com.xasdify.pocketflow.transactions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xasdify.pocketflow.transactions.data.entities.CategoryEntity


@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE type = :type")
    suspend fun getCategoriesByType(type: String): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("DELETE FROM categories  WHERE id = :id")
    suspend fun deleteCategoryById(id: Int)

    @Query("SELECT * FROM categories WHERE id = :id LIMIT 1")
    suspend fun getCategoryById(id: Int): CategoryEntity?
}