package com.sudo248.sudoo.data.dto.discovery

import java.time.LocalDateTime

data class ProductInfoDto(
    val productId: String,
    val name: String,
    val sku: String,
    val images: List<String>,
    val price: Double,
    val listedPrice: Double,
    val amount: Int,
    val rate: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime,
    val endDateDiscount: LocalDateTime,
    val saleable: Boolean,
    val brand: String,
)
