package com.sudoo.productservice.dto

import com.sudoo.domain.base.Pagination

data class CommentPagination<out T> (
    val comments: List<T>,
    val pagination: Pagination
)