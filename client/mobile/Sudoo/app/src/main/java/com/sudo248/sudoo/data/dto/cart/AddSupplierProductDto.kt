package com.sudo248.sudoo.data.dto.cart

data class AddSupplierProductDto(
    val cartProductId: String,
    val productId: String,
    val quantity: Int
)
