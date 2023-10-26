package com.sudo248.sudoo.domain.entity.order

data class OrderCartProduct(
    val cartProductId: String,
    val cartId: String,
    val quantity: Int,
    val totalPrice: Double,
    val product: OrderProductInfo
)