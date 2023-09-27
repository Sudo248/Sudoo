package com.sudoo.productservice.dto

import java.time.LocalDateTime

data class ProductInfoDto(
    val productId: String,
    val sku: String,
    val name: String,
    val price: Float,
    val listedPrice: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime?,
    val endDateDiscount: LocalDateTime?,
    val brand: String,
    val rate: Float,
    val sellable: Boolean,
    val images: List<String> = emptyList(),
)
