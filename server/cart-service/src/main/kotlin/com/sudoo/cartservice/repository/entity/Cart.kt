package com.sudoo.cartservice.repository.entity

import com.sudoo.cartservice.controller.dto.CartDto
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "cart")
data class Cart(
        @Id
        @Column("cart_id")
        var cartId: String = "",

        @Column("total_price")
        var totalPrice: Double = 0.0,

        @Column("total_amount")
        var totalAmount: Int = 0,

        @Column("user_id")
        var userId: String = "",

        @Column("status")
        var status: String = "completed",


        ) : Persistable<String> {
    @Transient
    var cartProducts: List<CartProduct> = listOf()

    @Transient
    internal var isNewCart: Boolean = false
    override fun getId(): String = cartId
    override fun isNew(): Boolean = isNewCart
}

fun Cart.toCartDto(): CartDto {
    return CartDto(
            cartId = this.cartId,
            totalPrice = this.totalPrice,
            totalAmount = this.totalAmount,
            status = this.status
    )
}