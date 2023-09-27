package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("suppliers")
data class Supplier(
    @Id
    @Column("supplier_id")
    val supplierId: String,

    @Column("user_id")
    val userId: String,

    @Column("name")
    val name: String,

    @Column("avatar")
    val avatar: String,

    @Column("brand")
    val brand: String,

    @Column("longitude")
    val longitude: Double,

    @Column("latitude")
    val latitude: Double,

    @Column("location_name")
    val locationName: String,

    @Column("contact_url")
    val contactUrl: String,

    @Column("rate")
    val rate: Float,

    @Column("create_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

    @Transient
    val products: List<Product>? = null
)
