package com.sudo248.sudoo.data.dto.cart

data class UpsertCartProductDto(
    val cartProductId: String? = null,
    val productId: String,
    val quantity: Int,
)
