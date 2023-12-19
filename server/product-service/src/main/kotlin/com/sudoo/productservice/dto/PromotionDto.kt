package com.sudoo.productservice.dto

data class PromotionDto(
    val promotionId: String?,
    val supplierId: String?,
    val name: String,
    val value: Float,
    val enable: Boolean,
    val image: String,
    val totalAmount: Int
)
