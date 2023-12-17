package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("users_products")
data class UserProduct(
    @Id
    @Column("user_product_id")
    val userProductId: String,

    @Column("product_id")
    val productId: String,

    @Column("user_id")
    val userId: String,

    @Column("rate")
    val rate: Float,

    @Column("is_like")
    val isLike: Boolean,

    @Column("comment")
    val comment: String,

    @Column("create_at")
    val createAt: LocalDateTime = LocalDateTime.now(),

) : Persistable<String> {

    @Transient
    var images: List<Image>? = null

    @Transient
    internal var isNewUserProduct: Boolean = false

    override fun getId(): String = userProductId

    override fun isNew(): Boolean = isNewUserProduct
}
