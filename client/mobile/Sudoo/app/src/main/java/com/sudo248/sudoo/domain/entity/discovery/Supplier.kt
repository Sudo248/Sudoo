package com.sudo248.sudoo.domain.entity.discovery

import java.time.LocalDateTime

data class Supplier(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val location: Location,
    val contactUrl: String,
    val locationName: String,
    val totalProducts: Int,
    val rate: Float,
    val createAt: LocalDateTime,
)