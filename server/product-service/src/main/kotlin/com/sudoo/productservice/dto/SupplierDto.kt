package com.sudoo.productservice.dto

import java.time.LocalDateTime

data class SupplierDto(
    val supplierId: String? = null,
    val name: String,
    val avatar: String,
    val brand: String,
    val location: Location,
    val contactUrl: String,
    val locationName: String,
    val totalProducts: Int? = null,
    val rate: Float? = null,
    val createAt: LocalDateTime?,
)