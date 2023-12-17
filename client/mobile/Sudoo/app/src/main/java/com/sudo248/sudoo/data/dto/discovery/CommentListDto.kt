package com.sudo248.sudoo.data.dto.discovery

data class CommentListDto(
    val comments: List<CommentDto>,
    val pagination: PaginationDto,
)
