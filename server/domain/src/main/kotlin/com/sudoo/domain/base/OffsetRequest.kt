package com.sudoo.domain.base

data class OffsetRequest(
    val offset: Int = 0,
    val limit: Int = 10
)
