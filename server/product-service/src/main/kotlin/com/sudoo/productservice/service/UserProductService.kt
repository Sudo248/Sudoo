package com.sudoo.productservice.service

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.base.PaginationDto
import com.sudoo.productservice.dto.UserProductDto
import kotlinx.coroutines.flow.Flow

interface UserProductService {
    suspend fun upsertUserProduct(userId: String, userProductDto: UserProductDto): UserProductDto
    suspend fun deleteUserProduct(userId: String, userProductId: String): String

    suspend fun getUserProductByProductId(userId: String, productId: String, offsetRequest: OffsetRequest): PaginationDto<Flow<UserProductDto>>
}