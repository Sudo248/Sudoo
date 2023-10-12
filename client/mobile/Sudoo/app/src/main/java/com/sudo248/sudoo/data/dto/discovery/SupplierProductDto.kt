package com.sudo248.sudoo.data.dto.discovery

import com.sudo248.sudoo.domain.entity.discovery.Route

data class SupplierProductDto(
    val supplierId: String,
    val productId: String,
    val route: Route,
    val amountLeft: Int,
    val price: Double,
    val soldAmount: Int,
    val rate: Double,
)
