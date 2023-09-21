package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("products")
data class Product(
    @Id
    @Column("product_id")
    val productId: String? = null,

    @Column("supplier_id")
    val supplierId: String,

    @Column("sku")
    val sku: String,

    @Column("name")
    val name: String,

    @Column("description")
    val description: String,

    @Column("price")
    val price: Float,

    @Column("listed_price")
    val listedPrice: Float,

    @Column("amount")
    val amount: Int,

    @Column("sold_amount")
    val soldAmount: Int,

    @Column("rate")
    val rate: Float,

    @Column("total_rate_amount")
    val totalRateAmount: Int,

    @Column("discount")
    val discount: Int,

    @Column("start_date_discount")
    val startDateDiscount: LocalDateTime,

    @Column("end_date_discount")
    val endDateDiscount: LocalDateTime,

    @Column("sellable")
    val sellable: Boolean,

    @Transient
    val images: List<String>? = null,

    @Transient
    val supplier: Supplier? = null,

    @Transient
    val categories: List<Category>? = null,
)