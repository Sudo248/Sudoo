package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.cart.AddSupplierProductDto
import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.cart.CartSupplierProductDto
import com.sudo248.sudoo.data.dto.cart.SupplierProductDetailDto
import com.sudo248.sudoo.domain.entity.cart.AddSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.Cart
import com.sudo248.sudoo.domain.entity.cart.CartSupplierProduct
import com.sudo248.sudoo.domain.entity.cart.SupplierProductDetail

fun SupplierProductDetailDto.toSupplierProductDetail(): SupplierProductDetail {
    return SupplierProductDetail(
        supplier = supplier.toSupplier(),
        product = product.toProduct(),
        route = route,
        amountLeft, price, soldAmount, rate
    )
}

fun CartSupplierProductDto.toCartSupplierProduct(): CartSupplierProduct {
    return CartSupplierProduct(
        supplierProduct = supplierProduct.toSupplierProductDetail(),
        amount, totalPrice, cartId
    )
}

fun CartDto.toCart(): Cart {
    return Cart(
        cartId = cartId,
        totalPrice = totalPrice,
        totalAmount = totalAmount,
        status = status,
        cartSupplierProducts = cartSupplierProducts.map { it.toCartSupplierProduct() }
    )
}

fun AddSupplierProduct.toAddSupplierProductDto(): AddSupplierProductDto {
    return AddSupplierProductDto(
        supplierId, productId, amount
    )
}