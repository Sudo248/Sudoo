package com.sudo248.sudoo.data.dto.cart

data class CartProductDto(
    val cartProductId: String = "",
    val cartId: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val product: ProductInfoDto? = null
)
