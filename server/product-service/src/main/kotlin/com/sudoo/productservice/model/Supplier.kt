package com.sudoo.productservice.model

import com.sudoo.domain.common.Constants
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.time.ZoneId

@Table("suppliers")
data class Supplier(
    @Id
    @Column("supplier_id")
    val supplierId: String,

    @Column("user_id")
    val userId: String,

    @Column("ghn_shop_id")
    val ghnShopId: Int,

    @Column("name")
    val name: String,

    @Column("avatar")
    val avatar: String,

    @Column("address_id")
    var addressId: String,

    @Column("contact_url")
    val contactUrl: String,

    @Column("rate")
    var rate: Float,

    @Column("total_revenue")
    var totalRevenue: Double = 0.0,

    @Column("income")
    var income: Double = 0.0,

    @Column("create_at")
    val createAt: LocalDateTime = LocalDateTime.now(ZoneId.of(Constants.zoneId)),
) : Persistable<String> {

    @Transient
    val products: List<Product>? = null

    @Transient
    internal var isNewSupplier: Boolean = false

    override fun getId(): String = supplierId

    override fun isNew(): Boolean = isNewSupplier
}
