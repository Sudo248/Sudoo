package com.sudo248.sudoo.data.dto.discovery

import com.sudo248.sudoo.data.dto.user.AddressDto
import java.time.LocalDateTime

data class SupplierDto(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val contactUrl: String,
    val totalProducts: Int,
    val rate: Float,
    val createAt: LocalDateTime,
    val address: AddressDto,
)