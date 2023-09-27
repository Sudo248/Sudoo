package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto
import com.sudoo.productservice.mapper.toCategory
import com.sudoo.productservice.mapper.toCategoryDto
import com.sudoo.productservice.repository.CategoryRepository
import com.sudoo.productservice.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
) : CategoryService {
    override suspend fun getCategories(): Flow<CategoryDto> {
        return categoryRepository.findAll().map { it.toCategoryDto() }
    }

    override suspend fun getCategoryInfos(): Flow<CategoryInfoDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(categoryId: String): CategoryDto {
        val category = categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        return category.toCategoryDto()
    }

    override suspend fun upsertCategory(categoryDto: CategoryDto): CategoryDto {
        val category = categoryDto.toCategory()
        categoryRepository.save(category)
        return category.toCategoryDto()
    }

    override suspend fun deleteCategory(categoryId: String): String {
        val category = categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        categoryRepository.delete(category)
        return categoryId
    }
}