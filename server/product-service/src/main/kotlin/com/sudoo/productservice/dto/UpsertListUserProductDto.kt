package com.sudoo.productservice.dto

data class UpsertListUserProductDto(
    val userId: String,
    val supplierId: String,
    val productIds: List<String>
)