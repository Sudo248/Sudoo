package com.sudoo.productservice.dto

data class OrderProductInfoDto(
    val productId: String? = null,
    val supplierId: String,
    val sku: String? = null,
    val name: String,
    val price: Float,
    val weight: Float,
    val height: Float,
    val length: Float,
    val width: Float,
    val brand: String,
    val images: List<String>? = null,
)
