package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.sudoo.domain.entity.user.Address
import java.time.LocalDateTime

data class Supplier(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val contactUrl: String,
    val totalProducts: Int,
    val rate: Float,
    val createAt: LocalDateTime,
    val address: Address
)