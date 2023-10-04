package com.sudoo.cartservice.repository.entity

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import javax.persistence.*

@Table(name = "cartSupplierProduct")
data class CartSupplierProduct(

        @EmbeddedId
        var id: SupplierProductKey,

        @Column("amount")
        var amount: Int? = null,

        @Column("total_price")
        var totalPrice: Double? = null,

        @Column("cart_id")
        var cart: Cart? = null

)