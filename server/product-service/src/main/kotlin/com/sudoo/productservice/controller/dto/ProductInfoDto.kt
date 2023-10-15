package com.sudoo.productservice.controller.dto

data class ProductInfoDto(
    val productId: String,
    val sku: String,
    val name: String,
    val price: Float,
    val listedPrice: Float,
    val discount: Int,
    val brand: String,
    val sellable: Boolean,
    val images: List<String> = emptyList(),
)
