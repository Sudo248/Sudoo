package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto
import com.sudoo.productservice.mapper.toCategory
import com.sudoo.productservice.mapper.toCategoryDto
import com.sudoo.productservice.repository.CategoryProductRepository
import com.sudoo.productservice.repository.CategoryRepository
import com.sudoo.productservice.service.CategoryService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
    private val categoryProductRepository: CategoryProductRepository,
) : CategoryService {
    override suspend fun getCategories(select: String): List<CategoryDto> = coroutineScope{
        // customer get categories
        if (select.isBlank()) {
            categoryRepository.findCategoriesByEnable(true).map { it.toCategoryDto() }.toList()
        } else {
            // admin get categories
            categoryRepository.findAll().map {
                async {
                    val countProduct = categoryProductRepository.countProductOfCategory(it.categoryId).toInt()
                    it.toCategoryDto(countProduct = countProduct)
                }
            }.toList().awaitAll()
        }
    }

    override suspend fun getCategoryInfos(): List<CategoryInfoDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(categoryId: String): CategoryDto {
        val category =
            categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        val countProduct = categoryProductRepository.countProductOfCategory(category.categoryId).toInt()
        return category.toCategoryDto(countProduct = countProduct)
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
        return if (category.isNew) {
            category.toCategoryDto(countProduct = 0)
        } else {
            val countProduct = categoryProductRepository.countProductOfCategory(category.categoryId).toInt()
            category.toCategoryDto(countProduct = countProduct)
        }
    }

    override suspend fun deleteCategory(categoryId: String): String {
        val category =
            categoryRepository.findById(categoryId) ?: throw NotFoundException("Not found category $categoryId")
        categoryRepository.delete(category)
        return categoryId
    }
}