package com.sudoo.cartservice.repository.entity

import com.sudoo.cartservice.controller.dto.CartProductDto
import com.sudoo.cartservice.controller.dto.ProductInfoDto
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
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

    @Column("total_amount")
    var quantity: Int = 0,
): Persistable<String> {

    @Transient
    internal var isNewCartProduct: Boolean = false
    override fun getId(): String = cartProductId
    override fun isNew(): Boolean = isNewCartProduct
}

fun CartProduct.toCartProductDto(product:ProductInfoDto): CartProductDto {
    return CartProductDto(
        cartProductId = this.cartProductId,
        cartId = this.cartId,
        quantity = this.quantity,
        product = product
    )
}