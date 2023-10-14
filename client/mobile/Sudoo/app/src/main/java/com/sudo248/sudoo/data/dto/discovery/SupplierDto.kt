package com.sudo248.sudoo.data.dto.discovery

import java.time.LocalDateTime

data class SupplierDto(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val location: LocationDto,
    val contactUrl: String,
    val locationName: String,
    val totalProducts: Int,
    val rate: Float,
    val createAt: LocalDateTime,
)