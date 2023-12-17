package com.sudo248.sudoo.data.dto.discovery

data class ReviewListDto(
    val reviews: List<ReviewDto>,
    val pagination: PaginationDto,
)