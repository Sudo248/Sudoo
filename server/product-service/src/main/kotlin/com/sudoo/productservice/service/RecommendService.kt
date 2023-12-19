package com.sudoo.productservice.service

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.productservice.dto.RecommendListProductDto
import com.sudoo.productservice.dto.RecommendUserProductDto

interface RecommendService {
    suspend fun getListRecommendProduct(userId: String, offsetRequest: OffsetRequest): RecommendListProductDto
    suspend fun upsertProduct(productId: String)
    suspend fun upsertUserProduct(userProduct: RecommendUserProductDto)
}