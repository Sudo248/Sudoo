package com.sudo248.sudoo.domain.entity.discovery

data class CommentList(
    val comments: List<Comment>,
    val pagination: Pagination,
)
