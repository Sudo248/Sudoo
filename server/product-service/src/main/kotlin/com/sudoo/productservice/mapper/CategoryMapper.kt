package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.CategoryDto
import com.sudoo.productservice.dto.CategoryInfoDto
import com.sudoo.productservice.model.Category

fun Category.toCategoryDto(countProduct: Int? = null): CategoryDto {
    return CategoryDto(
        categoryId = categoryId,
        name = name,
        image = image,
        countProduct = countProduct,
    )
}

fun Category.toCategoryInfoDto(): CategoryInfoDto {
    return CategoryInfoDto(
        categoryId = categoryId,
        name = name,
        image = image,
    )
}

fun CategoryDto.toCategory(): Category {
    return Category(
        categoryId = IdentifyCreator.createOrElse(categoryId),
        name = name,
        image = image,
    ).also {
        it.isNewCategory = categoryId.isNullOrEmpty()
    }
}