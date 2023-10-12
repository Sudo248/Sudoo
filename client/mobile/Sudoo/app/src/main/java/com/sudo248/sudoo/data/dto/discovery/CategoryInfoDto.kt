package com.sudo248.sudoo.data.dto.discovery

data class CategoryInfoDto(
    val categoryId: String,
    val name: String,
    val image: String,
    val supplierId: String? = null,
)
