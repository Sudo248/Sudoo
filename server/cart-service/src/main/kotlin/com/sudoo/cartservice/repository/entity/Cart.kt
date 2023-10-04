package com.sudoo.cartservice.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.*

@Table(name = "cart")
data class Cart(
        @Id
        @Column("cart_id")
        var cardId: String? = null,

        @Column("total_price")
        var totalPrice: Double? = null,

        @Column("total_amount")
        var totalAmount: Int? = null,

        @Column("user_id")
        var userId: String? = null,

        @Column("status")
        var status: String? = null,

        @Column("cart")
        var cartSupplierProduct: List<CartSupplierProduct> = listOf()

)