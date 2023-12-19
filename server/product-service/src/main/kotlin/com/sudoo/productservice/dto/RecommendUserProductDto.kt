package com.sudoo.productservice.dto

data class RecommendUserProductDto(
    val userProductId: String,
    val userId: String,
    val productId: String,
    val rating: Float
)