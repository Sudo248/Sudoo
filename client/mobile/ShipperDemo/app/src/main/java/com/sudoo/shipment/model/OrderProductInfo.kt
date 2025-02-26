package com.sudoo.shipment.model

data class OrderProductInfo(
    val productId: String,
    val supplierId: String,
    val name: String,
    val sku: String,
    val images: List<String>,
    val price: Double,
    val brand: String,
    val weight: Int,
    val height: Int,
    val length: Int,
    val width: Int,
)