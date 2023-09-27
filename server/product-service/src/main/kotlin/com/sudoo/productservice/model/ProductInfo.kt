package com.sudoo.productservice.model

import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class ProductInfo(
    @Column("product_id")
    val productId: String,
    @Column("sku")
    val sku: String,
    @Column("name")
    val name: String,
    @Column("price")
    val price: Float,
    @Column("listed_price")
    val listedPrice: Float,
    @Column("sellable")
    val sellable: Boolean,
    @Column("rate")
    val rate: Float,
    @Column("discount")
    val discount: Int,
    @Column("start_date_discount")
    val startDateDiscount: LocalDateTime?,
    @Column("end_date_discount")
    val endDateDiscount: LocalDateTime?,

    @Transient
    var brand: String = "",

    @Transient
    var images: List<String>? = null,
)