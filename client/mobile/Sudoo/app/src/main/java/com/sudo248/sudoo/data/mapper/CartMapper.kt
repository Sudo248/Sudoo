package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.cart.AddCartProductDto
import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.cart.CartProductDto
import com.sudo248.sudoo.data.dto.cart.SupplierProductDetailDto
import com.sudo248.sudoo.domain.entity.cart.AddCartProduct
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.cart.CartProduct
import com.sudo248.sudoo.domain.entity.cart.SupplierProductDetail

fun SupplierProductDetailDto.toSupplierProductDetail(): SupplierProductDetail {
    return SupplierProductDetail(
        supplier = supplier.toSupplier(),
        product = product.toProduct(),
        amountLeft, price, soldAmount, rate
    )
}

fun CartProductDto.toCartProduct(): CartProduct {
    return CartProduct(
        cartProductId = this.cartProductId,
        cartId = this.cartId,
        quantity = this.quantity,
        totalPrice = this.totalPrice,
        product = this.product
    )
}

fun CartProduct.toCartProductDto(): CartProductDto {
    return CartProductDto(
        cartProductId = this.cartProductId,
        cartId = this.cartId,
        quantity = this.quantity,
        totalPrice = this.totalPrice,
        product = this.product
    )
}


fun CartDto.toCart(): Cart {
    return Cart(
        cartId = cartId,
        totalPrice = totalPrice,
        quantity = quantity,
        status = status,
        cartProducts = cartProducts.map { it.toCartProduct() }
    )
}

fun AddCartProduct.toAddSupplierProductDto(): AddCartProductDto {
    return AddCartProductDto("",
        productId, amount
    )
}