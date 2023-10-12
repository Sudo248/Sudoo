package com.sudo248.sudoo.data.dto.discovery

data class ProductDto(
    val productId: String,
    val name: String,
    val description: String,
    val sku: String,
    val images: List<ImageDto>? = null,
    val categoryIds: List<String> = listOf(),
    val supplierProducts: List<SupplierProductDto>? = null,
)