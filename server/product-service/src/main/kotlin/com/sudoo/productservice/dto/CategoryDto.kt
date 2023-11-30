package com.sudoo.productservice.dto

data class CategoryDto(
    val categoryId: String? = null,
    val name: String,
    val image: String,
    val enable: Boolean = true,
    val countProduct: Int? = null,
)