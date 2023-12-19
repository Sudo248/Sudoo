package com.sudoo.productservice.dto

data class RecommendListProductDto(
    val total: Long,
    val products: List<String>
)