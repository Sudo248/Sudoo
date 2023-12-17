package com.sudo248.sudoo.domain.entity.cart

import com.sudo248.sudoo.data.dto.cart.AddCartProductsDto
import com.sudo248.sudoo.data.dto.cart.CartProductsDto
import com.sudo248.sudoo.data.mapper.toCartProductDto

data class AddCartProducts(
    val cartProducts: List<CartProduct>
)

fun AddCartProducts.toAddCartProductsDto(): CartProductsDto {
    return CartProductsDto(
        cartProducts = this.cartProducts.map { it.toCartProductDto()}
    )
}