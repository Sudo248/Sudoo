package com.sudoo.cartservice.controller.dto.order

import com.sudoo.cartservice.controller.dto.toCartProduct
import com.sudoo.cartservice.repository.entity.CartProduct

data class OrderCartProductDto(
    val cartProductId: String = "",
    val cartId: String = "",
    val quantity: Int = 0,
    val purchasePrice :Double? = null,
    val totalPrice: Double = 0.0,
    val product: OrderProductInfoDto? = null
)

fun OrderCartProductDto.toCartProduct(): CartProduct {
    return CartProduct(
        cartProductId = this.cartProductId,
        cartId = this.cartId,
        purchasePrice = this.purchasePrice,
        quantity = this.quantity,
        productId = product?.productId ?: ""
    )
}