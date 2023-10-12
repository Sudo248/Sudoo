package com.sudo248.sudoo.domain.entity.cart

import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.Route
import com.sudo248.sudoo.domain.entity.discovery.Supplier

data class SupplierProductDetail(
    val supplier: Supplier,
    val product: Product,
    val route: Route = Route(),
    val amountLeft: Int,
    val price: Double,
    val soldAmount: Double,
    val rate: Double,
)
