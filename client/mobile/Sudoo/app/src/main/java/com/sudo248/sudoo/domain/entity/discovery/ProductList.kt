package com.sudo248.sudoo.domain.entity.discovery

data class ProductList(
    val products: List<ProductInfo>,
    val pagination: Pagination,
)
