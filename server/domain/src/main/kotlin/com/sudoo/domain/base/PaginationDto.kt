package com.sudoo.domain.base

data class PaginationDto<out T>(
    val data: T,
    val pagination: Pagination
)
