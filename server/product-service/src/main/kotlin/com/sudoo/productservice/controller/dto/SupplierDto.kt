package com.sudoo.productservice.controller.dto

import java.time.LocalDateTime

data class SupplierDto(
    val supplierId: String? = null,
    val userId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val location: Location,
    val contactUrl: String,
    val locationName: String,
    val totalProducts: Int,
    val rate: Float,
    val createAt: LocalDateTime = LocalDateTime.now(),
)