package com.sudo248.sudoo.data.dto.discovery

data class CategoryDto(
    val categoryId: String,
    val name: String,
    val image: String,
    val supplierId: String? = null,
    val products: List<ProductDto>,
)
