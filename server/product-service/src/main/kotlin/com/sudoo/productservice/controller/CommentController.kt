package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.UserProductDto
import com.sudoo.productservice.service.UserProductService
import io.lettuce.core.BitFieldArgs.Offset
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/discovery")
class CommentController(
    private val userProductService: UserProductService,
) : BaseController() {
    @PostMapping("/comments")
    suspend fun upsertComment(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody comment: UserProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.upsertComment(userId, comment)
    }

    @PostMapping("/reviews")
    suspend fun upsertReviewed(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody comment: UserProductDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.upsertComment(userId, comment)
    }

    @DeleteMapping("comments/{commentId}")
    suspend fun deleteComment(
        @PathVariable("commentId") commentId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.deleteComment(commentId)
    }

    @DeleteMapping("reviews/{reviewId}")
    suspend fun deleteReviewed(
        @PathVariable("reviewId") reviewId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        userProductService.deleteComment(reviewId)
    }

    @GetMapping("/comments")
    suspend fun getComments(
        @RequestParam("productId") productId: String?,
        @RequestParam("isReviewed", required = false) isReViewed: Boolean?,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        productId?.let {
            isReViewed?.let {
                userProductService.getCommentsByProductIdAndReviewed(productId, it,  offsetRequest)
            } ?: userProductService.getCommentsByProductId(productId, offsetRequest)
        } ?: userProductService.getComments(offsetRequest)
    }

    @GetMapping("/reviews")
    suspend fun getReviews(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestParam("isReviewed", required = false) isReViewed: Boolean?,
        @RequestParam("offset", required = false, defaultValue = Constants.DEFAULT_OFFSET) offset: Int,
        @RequestParam("limit", required = false, defaultValue = Constants.DEFAULT_LIMIT) limit: Int,
    ): ResponseEntity<BaseResponse<*>> = handle {
        val offsetRequest = OffsetRequest(offset, limit)
        isReViewed?.let {
            userProductService.getCommentsByUserIdAndReviewed(userId, it,  offsetRequest)
        } ?: userProductService.getCommentsByUserId(userId, offsetRequest)
    }
}