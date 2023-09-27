package com.sudoo.productservice.service

import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    suspend fun getCategories(): Flow<CategoryDto>
    suspend fun getCategoryInfos(): Flow<CategoryInfoDto>
    suspend fun getCategoryById(categoryId: String): CategoryDto

    suspend fun upsertCategory(categoryDto: CategoryDto): CategoryDto

    suspend fun deleteCategory(categoryId: String): String
}