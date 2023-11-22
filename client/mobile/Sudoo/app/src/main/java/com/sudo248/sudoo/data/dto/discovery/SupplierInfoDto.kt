package com.sudo248.sudoo.data.dto.discovery

import com.sudo248.sudoo.data.dto.user.AddressDto

data class SupplierInfoDto(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val contactUrl: String,
    val rate: Float,
    val address: AddressDto
)
