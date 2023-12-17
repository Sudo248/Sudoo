package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
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
    var rate: Float,

    @Column("create_at")
    val createAt: LocalDateTime = LocalDateTime.now(),
) : Persistable<String> {

    @Transient
    val products: List<Product>? = null

    @Transient
    internal var isNewSupplier: Boolean = false

    override fun getId(): String = supplierId

    override fun isNew(): Boolean = isNewSupplier
}
