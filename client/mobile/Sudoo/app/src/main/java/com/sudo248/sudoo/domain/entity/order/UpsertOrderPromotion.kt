package com.sudo248.sudoo.domain.entity.order

data class UpsertOrderPromotion(
    val promotionId: String,
    val orderSupplierId: String? = null,
    val totalPrice: Double = 0.0,
    val totalPromotionPrice: Double = 0.0,
    val finalPrice: Double = 0.0,
)