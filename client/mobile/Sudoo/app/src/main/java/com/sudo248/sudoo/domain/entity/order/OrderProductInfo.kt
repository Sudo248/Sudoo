package com.sudo248.sudoo.domain.entity.order

data class OrderProductInfo(
    val productId: String,
    val supplierId: String,
    val name: String,
    val sku: String,
    val images: List<String>,
    val price: Double,
    val brand: String,
    val weight: Float,
    val height: Float,
    val length: Float,
    val width: Float,
)
