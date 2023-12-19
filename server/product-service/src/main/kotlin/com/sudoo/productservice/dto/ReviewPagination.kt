package com.sudoo.productservice.dto

import com.sudoo.domain.base.Pagination

data class ReviewPagination<out T> (
    val reviews: List<T>,
    val pagination: Pagination
)