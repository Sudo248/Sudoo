package com.sudoo.cartservice.controller.dto.order

import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.repository.entity.Cart

data class OrderCartDto(
    val userId: String = "",
    val cartId: String = "",
    var totalPrice: Double = 0.0,
    var quantity: Int = 0,
    val status: String = "",
    var cartProducts: List<OrderCartProductDto> = listOf()
)

fun OrderCartDto.toCart(): Cart {
    return Cart(userId = this.userId, cartId = this.cartId, quantity = this.quantity, status = this.status)
}