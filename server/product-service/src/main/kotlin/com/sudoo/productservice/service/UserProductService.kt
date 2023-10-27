package com.sudoo.productservice.service

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.productservice.dto.CommentPagination
import com.sudoo.productservice.dto.UserProductDto

interface UserProductService {
    suspend fun upsertComment(userId: String, userProductDto: UserProductDto): UserProductDto

    suspend fun deleteComment(userProductId: String): String

    suspend fun getComments(offsetRequest: OffsetRequest): CommentPagination<UserProductDto>

    suspend fun getCommentsByProductId(
        productId: String,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto>

    suspend fun getCommentsByUserId(
        userId: String,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto>

    suspend fun getCommentsByProductIdAndReviewed(
        productId: String,
        isReviewed: Boolean,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto>

    suspend fun getCommentsByUserIdAndReviewed(
        userId: String,
        isReviewed: Boolean,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto>
}