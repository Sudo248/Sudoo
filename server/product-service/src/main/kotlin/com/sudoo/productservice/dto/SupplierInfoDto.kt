package com.sudoo.productservice.dto

data class SupplierInfoDto(
    val supplierId: String? = null,
    val userId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val locationName: String,
    val contactUrl: String,
    val rate: Float
)