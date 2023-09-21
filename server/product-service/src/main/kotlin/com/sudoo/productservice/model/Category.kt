package com.sudoo.productservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("categories")
data class Category(
    @Id
    @Column("category_id")
    val categoryId: String? = null,

    @Column("name")
    val name: String,

    @Column("image")
    val image: String,

    @Transient
    val products: List<Product>? = null
)