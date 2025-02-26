package com.sudo248.sudoo.data.dto.cart

data class AddCartProductDto(
    val cartProductId: String,
    val productId: String,
    val quantity: Int
)
