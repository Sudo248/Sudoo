package com.sudoo.productservice.service

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.productservice.dto.CommentPagination
import com.sudoo.productservice.dto.UserProductDto

interface UserProductService {
    suspend fun upsertComment(userId: String, userProductDto: UserProductDto): UserProductDto
    suspend fun deleteComment(userProductId: String): String

    suspend fun getCommentsByProductId(
        productId: String,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto>
}