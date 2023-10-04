package com.sudoo.domain.base

data class BasePageDto<out T>(
    val data: T,
    val pagination: Pagination
)
