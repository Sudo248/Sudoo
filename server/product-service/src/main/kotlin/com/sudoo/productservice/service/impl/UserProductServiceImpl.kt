package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.Pagination
import com.sudoo.domain.exception.BadRequestException
import com.sudoo.productservice.dto.CommentPagination
import com.sudoo.productservice.dto.UserProductDto
import com.sudoo.productservice.mapper.toUserProduct
import com.sudoo.productservice.mapper.toUserProductDto
import com.sudoo.productservice.model.Image
import com.sudoo.productservice.repository.ImageRepository
import com.sudoo.productservice.repository.UserProductRepository
import com.sudoo.productservice.service.CoreService
import com.sudoo.productservice.service.UserProductService
import com.sudoo.productservice.service.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class UserProductServiceImpl(
    private val coreService: CoreService,
    private val userProductRepository: UserProductRepository,
    private val imageRepository: ImageRepository,
    private val userService: UserService,
) : UserProductService {
    override suspend fun upsertComment(userId: String, userProductDto: UserProductDto): UserProductDto =
        coroutineScope {
            val userProduct = userProductDto.toUserProduct(userId)
            userProductRepository.save(userProduct)
            if (userProduct.isNew) {
                coreService.upsertComment(userProduct)
                userProduct.images = userProductDto.images?.map {
                    async {
                        imageRepository.save(Image.from(userProduct.userProductId, it))
                    }
                }?.awaitAll()
            } else {
                userProductRepository.findById(userProduct.userProductId)?.let {
                    userProduct.createdAt = it.createdAt
                }
            }
            userProductRepository.save(userProduct)
            userProduct.toUserProductDto(userInfo = userService.getUserInfo(userId))
        }

    override suspend fun deleteComment(userProductId: String): String = coroutineScope {
        userProductRepository.findById(userProductId) ?: throw BadRequestException("Not found comment or review $userProductId")
        userProductRepository.deleteById(userProductId)
        userProductId
    }

    override suspend fun getComments(offsetRequest: OffsetRequest): CommentPagination<UserProductDto> = coroutineScope {
        val totalCount = async { userProductRepository.count() }
        val comments = userProductRepository.getAllWithOffset(
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map { userProduct ->
            val userInfo = async { userService.getUserInfo(userProduct.userId) }
            userProduct.images = imageRepository.getAllByOwnerId(userProduct.userProductId).toList()
            userProduct.toUserProductDto(userInfo = userInfo.await())
        }
        CommentPagination(
            comments = comments.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + comments.count() + 1,
                total = totalCount.await(),
            )
        )
    }

    override suspend fun getCommentsByProductId(
        productId: String,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto> = coroutineScope {
        val totalCount = async { userProductRepository.countByProductId(productId) }
        val comments = userProductRepository.getAllByProductIdWithOffset(
            productId = productId,
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map { userProduct ->
            val userInfo = async { userService.getUserInfo(userProduct.userId) }
            userProduct.images = imageRepository.getAllByOwnerId(userProduct.userProductId).toList()
            userProduct.toUserProductDto(userInfo = userInfo.await())
        }
        CommentPagination(
            comments = comments.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + comments.count() + 1,
                total = totalCount.await(),
            )
        )
    }

    override suspend fun getCommentsByUserId(
        userId: String,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto> = coroutineScope {
        val totalCount = async { userProductRepository.countByUserId(userId) }
        val comments = userProductRepository.getAllByUserIdWithOffset(
            userId = userId,
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map { userProduct ->
            val userInfo = async { userService.getUserInfo(userProduct.userId) }
            userProduct.images = imageRepository.getAllByOwnerId(userProduct.userProductId).toList()
            userProduct.toUserProductDto(userInfo = userInfo.await())
        }
        CommentPagination(
            comments = comments.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + comments.count() + 1,
                total = totalCount.await(),
            )
        )
    }

    override suspend fun getCommentsByProductIdAndReviewed(
        productId: String,
        isReviewed: Boolean,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto> = coroutineScope {
        val totalCount = async { userProductRepository.countByProductIdAndReviewed(productId, isReviewed) }
        val comments = userProductRepository.getByProductIdAndReviewedWithOffset(
            productId,
            isReviewed,
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map { userProduct ->
            val userInfo = async { userService.getUserInfo(userProduct.userId) }
            userProduct.images = imageRepository.getAllByOwnerId(userProduct.userProductId).toList()
            userProduct.toUserProductDto(userInfo = userInfo.await())
        }
        CommentPagination(
            comments = comments.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + comments.count() + 1,
                total = totalCount.await(),
            )
        )
    }

    override suspend fun getCommentsByUserIdAndReviewed(
        userId: String,
        isReviewed: Boolean,
        offsetRequest: OffsetRequest
    ): CommentPagination<UserProductDto> = coroutineScope {
        val totalCount = async { userProductRepository.countByUserIdAndReviewed(userId, isReviewed) }
        val comments = userProductRepository.getByUserIdAndReviewedWithOffset(
            userId = userId,
            isReviewed = isReviewed,
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map { userProduct ->
            val userInfo = async { userService.getUserInfo(userProduct.userId) }
            userProduct.images = imageRepository.getAllByOwnerId(userProduct.userProductId).toList()
            userProduct.toUserProductDto(userInfo = userInfo.await())
        }
        CommentPagination(
            comments = comments.toList(),
            pagination = Pagination(
                offset = offsetRequest.offset + comments.count() + 1,
                total = totalCount.await(),
            )
        )
    }

}