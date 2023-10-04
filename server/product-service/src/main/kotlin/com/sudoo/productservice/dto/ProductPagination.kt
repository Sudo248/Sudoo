package com.sudoo.productservice.dto

import com.sudoo.domain.base.Pagination

data class ProductPagination<out T> (
    val products: List<T>,
    val pagination: Pagination
)