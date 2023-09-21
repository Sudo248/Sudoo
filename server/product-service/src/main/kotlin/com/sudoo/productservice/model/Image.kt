package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("images")
data class Image(
    @Id
    @Column("image_id")
    val imageId: String,

    @Column("owner_id")
    val ownerId: String,

    @Column("url")
    val url: String,
)
