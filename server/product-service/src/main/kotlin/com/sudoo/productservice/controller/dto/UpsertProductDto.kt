package com.sudoo.productservice.controller.dto

import java.time.LocalDateTime

data class UpsertProductDto(
    val productId: String? = null,
    val sku: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Float? = null,
    val listedPrice: Float? = null,
    val amount: Int? = null,
    val soldAmount: Int? = null,
    val discount: Int? = null,
    val startDateDiscount: LocalDateTime? = null,
    val endDateDiscount: LocalDateTime? = null,
    val sellable: Boolean? = null,
    val images: List<String>? = null,
    val supplierId: String? = null,
    val categoryIds: List<String>? = null,
)