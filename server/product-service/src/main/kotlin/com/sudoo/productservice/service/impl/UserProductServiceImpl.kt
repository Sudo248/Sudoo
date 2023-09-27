package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.Pagination
import com.sudoo.domain.base.PaginationDto
import com.sudoo.productservice.dto.UserProductDto
import com.sudoo.productservice.mapper.toUserProduct
import com.sudoo.productservice.mapper.toUserProductDto
import com.sudoo.productservice.model.Image
import com.sudoo.productservice.repository.ImageRepository
import com.sudoo.productservice.repository.UserProductRepository
import com.sudoo.productservice.service.UserProductService
import com.sudoo.productservice.service.UserService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class UserProductServiceImpl(
    private val userProductRepository: UserProductRepository,
    private val imageRepository: ImageRepository,
    private val userService: UserService,
) : UserProductService {
    override suspend fun upsertUserProduct(userId: String, userProductDto: UserProductDto): UserProductDto = coroutineScope {
        val isUpdate = userProductDto.userProductId != null
        val userProduct = userProductDto.toUserProduct(userId)
        userProductRepository.save(userProduct)
        if (!isUpdate) {
            userProductDto.images?.map {
                async {
                    imageRepository.save(Image.from(userProduct.userId, it))
                }
            }?.awaitAll()
        }
        userProduct.toUserProductDto(
            userInfo = userService.getUserInfo(),
            images = userProductDto.images
        )
    }

    override suspend fun deleteUserProduct(userId: String, userProductId: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProductByProductId(
        userId: String,
        productId: String,
        offsetRequest: OffsetRequest
    ): PaginationDto<Flow<UserProductDto>> = coroutineScope{
        val totalCount = async { userProductRepository.countByProductId(productId) }
        val data = userProductRepository.getAllByProductIdWithOffset(
            productId = productId,
            offset = offsetRequest.offset,
            limit = offsetRequest.limit
        ).map {  userProduct ->
            val userInfo = async { userService.getUserInfo() }
            val images = async { imageRepository.getAllByOwnerId(userProduct.userProductId).map { it.url }.toList() }
            userProduct.toUserProductDto(
                userInfo = userInfo.await(),
                images = images.await()
            )
        }
        PaginationDto(
            data = data,
            pagination = Pagination(
                offset = offsetRequest.offset + data.count() + 1,
                total = totalCount.await(),
            )
        )
    }

}