package com.sudoo.cartservice.repository.entity

import com.sudoo.cartservice.controller.dto.CartProductDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "cart_product")
data class CartProduct(
        @Id
        @Column("cart_product_id")
        var cartProductId: String = "",

        @Column("cart_id")
        var cartId: String = "",

        @Column("product_id")
        var productId: String = "",

        @Column("quantity")
        var quantity: Int = 0,

        @Column("total_price")
        var totalPrice: Double = 0.0
)

fun CartProduct.toCartProductDto(): CartProductDto {
    return CartProductDto(
            cartProductId = this.cartProductId,
            cartId = this.cartId,
            productId = this.productId,
            quantity = this.quantity,
            totalPrice = this.totalPrice
    )
}