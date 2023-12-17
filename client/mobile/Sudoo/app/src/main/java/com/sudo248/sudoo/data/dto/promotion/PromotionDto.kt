package com.sudo248.sudoo.data.dto.promotion

data class PromotionDto(
    val promotionId: String,
    val supplierId: String?,
    val value: Double,
    val name: String,
    val enable: Boolean,
    val image: String,
    val totalAmount: Int,
)
