package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto
import com.sudoo.productservice.mapper.toCategory
import com.sudoo.productservice.mapper.toCategoryDto
import com.sudoo.productservice.repository.CategoryRepository
import com.sudoo.productservice.service.CategoryService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override suspend fun getCategories(): List<CategoryDto> {
        return categoryRepository.findAll().map { it.toCategoryDto() }.toList()
    }

    override suspend fun getCategoryInfos(): List<CategoryInfoDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(categoryId: String): CategoryDto {
        val category =
            categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        return category.toCategoryDto()
    }

    override suspend fun getCategoriesByProductId(productId: String): List<CategoryDto> = coroutineScope {
        val categories = categoryRepository.getCategoryIdByProductId(productId)
            .map { categoryId ->
                val category = categoryRepository.findById(categoryId)
                category!!.toCategoryDto()
            }
        categories.toList()
    }

    override suspend fun upsertCategory(categoryDto: CategoryDto): CategoryDto {
        val category = categoryDto.toCategory()
        categoryRepository.save(category)
        return category.toCategoryDto()
    }

    override suspend fun deleteCategory(categoryId: String): String {
        val category =
            categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        categoryRepository.delete(category)
        return categoryId
    }
}