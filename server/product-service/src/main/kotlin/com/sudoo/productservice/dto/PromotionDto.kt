package com.sudoo.productservice.dto

data class PromotionDto(
    val promotionId: String?,
    val supplierId: String,
    val name: String,
    val value: Float,
    val totalAmount: Int
)
