package com.sudoo.cartservice.controller.dto

import com.sudoo.cartservice.repository.entity.CartProduct

data class CartProductDto(
        val cartProductId:String = "",
        val cartId: String = "",
        val productId: String = "",
        val quantity: Int = 0,
        val totalPrice: Double = 0.0
)


fun CartProductDto.toCartProduct():CartProduct{
    return CartProduct(cartProductId = this.cartProductId,cartId  =this.cartId,productId = this.productId, quantity = this.quantity, totalPrice = this.totalPrice)
}