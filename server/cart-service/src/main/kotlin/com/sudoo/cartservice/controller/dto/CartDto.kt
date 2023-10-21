package com.sudoo.cartservice.controller.dto

import com.sudoo.cartservice.repository.entity.Cart

data class CartDto(
    val userId: String = "",
    val cartId: String = "",
    var totalPrice: Double = 0.0,
    var totalAmount: Int = 0,
    val status: String = "",
    var cartProducts: List<CartProductDto> = listOf()
)

fun CartDto.toCart(): Cart {
    return Cart(userId = this.userId, cartId = this.cartId, totalAmount = this.totalAmount, status = this.status)
}