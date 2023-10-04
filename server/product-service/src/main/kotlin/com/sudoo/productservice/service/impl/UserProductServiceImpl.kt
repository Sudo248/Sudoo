package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.Pagination
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
                        imageRepository.save(Image.from(userProduct.userId, it))
                    }
                }?.awaitAll()
            }
            userProduct.toUserProductDto(userInfo = userService.getUserInfo(userId))
        }

    override suspend fun deleteComment(userProductId: String): String {
        TODO("Not yet implemented")
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

}