package com.sudo248.sudoo.data.dto.order

data class UpsertOrderPromotionDto(
    val promotionId: String,
    val orderSupplierId: String? = null,
    val totalPrice: Double = 0.0,
    val totalPromotionPrice: Double = 0.0,
    val finalPrice: Double = 0.0,
)
