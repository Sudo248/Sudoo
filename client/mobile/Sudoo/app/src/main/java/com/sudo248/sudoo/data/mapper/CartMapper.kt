package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.cart.AddSupplierProductDto
import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.cart.CartProductDto
import com.sudo248.sudoo.data.dto.cart.SupplierProductDetailDto
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
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

fun CartProductDto.toCartSupplierProduct(): CartProduct {
    return CartProduct(
        cartProductId = "",
        cartId = "",
        quantity = 0,
        totalPrice = 0.0,
        product = this.product
    )
}

fun CartDto.toCart(): Cart {
    return Cart(
        cartId = cartId,
        totalPrice = totalPrice,
        totalAmount = totalAmount,
        status = status,
        cartProducts = cartProducts.map { it.toCartSupplierProduct() }
    )
}

fun AddSupplierProduct.toAddSupplierProductDto(): AddSupplierProductDto {
    return AddSupplierProductDto(
        supplierId, productId, amount
    )
}