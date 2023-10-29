package com.sudo248.sudoo.domain.entity.discovery

import com.sudo248.sudoo.domain.entity.user.Address

data class SupplierInfo(
    val supplierId: String,
    val name: String,
    val avatar: String,
    val brand: String,
    val contactUrl: String,
    val rate: Float,
    val address: Address
)
