package com.sudoo.cartservice.controller.dto

import com.sudoo.cartservice.repository.entity.Cart

data class CartDto(
    val userId: String = "",
    val cartId: String = "",
    val totalPrice: Double = 0.0,
    val totalAmount: Int = 0,
    val status: String = "",
    val cartProducts: List<CartProductDto> = listOf()
)

fun CartDto.toCart(): Cart {
    return Cart(userId = this.userId, cartId = this.cartId, totalAmount = this.totalAmount, status = this.status)
}