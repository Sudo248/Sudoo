package com.sudoo.cartservice.controller.dto.order


data class OrderProductInfoDto(
    val productId: String? = "",
    val supplierId: String = "",
    val sku: String? = null,
    val name: String = "",
    val price: Float = 0.0f,
    val weight: Float = 0.0f,
    val height: Float = 0.0f,
    val length: Float = 0.0f,
    val width: Float = 0.0f,
    val brand: String = "",
    val images: List<String>? = listOf(),
)

