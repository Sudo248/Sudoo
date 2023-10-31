package com.sudoo.cartservice.controller.dto.order

import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.repository.entity.Cart

data class OrderCartDto(
    val userId: String = "",
    val cartId: String = "",
    var totalPrice: Double = 0.0,
    var totalAmount: Int = 0,
    val status: String = "",
    var cartProducts: List<OrderCartProductDto> = listOf()
)

fun OrderCartDto.toCart(): Cart {
    return Cart(userId = this.userId, cartId = this.cartId, totalAmount = this.totalAmount, status = this.status)
}