package com.sudoo.productservice.service

import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto

interface CategoryService {
    suspend fun getCategories(select: String): List<CategoryDto>
    suspend fun getCategoryInfos(): List<CategoryInfoDto>
    suspend fun getCategoryById(categoryId: String): CategoryDto
    suspend fun getCategoriesByProductId(productId: String): List<CategoryDto>

    suspend fun upsertCategory(categoryDto: CategoryDto): CategoryDto

    suspend fun deleteCategory(categoryId: String): String
}