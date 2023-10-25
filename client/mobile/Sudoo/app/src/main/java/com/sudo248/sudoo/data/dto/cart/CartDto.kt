package com.sudo248.sudoo.data.dto.cart

import java.time.LocalDateTime

data class CartDto(
    val userId: String = "",
    val cartId: String = "",
    var totalPrice: Double = 0.0,
    var totalAmount: Int = 0,
    val status: String = "",
    var cartSupplierProducts: List<CartSupplierProductDto> = listOf()
)







