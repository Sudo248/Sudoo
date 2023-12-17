package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.CategoryProductDto
import com.sudoo.productservice.model.CategoryProduct

fun CategoryProductDto.toCategoryProduct(): CategoryProduct {
    return CategoryProduct(
        categoryProductId = IdentifyCreator.createOrElse(categoryProductId),
        productId = productId!!,
        categoryId = categoryId!!,
    ).also {
        it.isNewCategoryProduct = categoryProductId.isNullOrEmpty()
    }
}

fun CategoryProduct.toCategoryProductDto(): CategoryProductDto {
    return CategoryProductDto(
        categoryProductId = categoryProductId,
        productId = productId,
        categoryId = categoryId,
    )
}