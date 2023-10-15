package com.sudoo.productservice.controller.dto

import java.time.LocalDateTime

data class ProductDto(
    val productId: String? = null,
    val sku: String? = null,
    val name: String,
    val description: String,
    val price: Float,
    val listedPrice: Float,
    val amount: Int,
    val soldAmount: Int,
    val rate: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime,
    val endDateDiscount: LocalDateTime,
    val sellable: Boolean,
    val images: List<String> = emptyList(),
    val supplier: SupplierInfoDto? = null,
    val categories: List<CategoryInfoDto>? = null,
)