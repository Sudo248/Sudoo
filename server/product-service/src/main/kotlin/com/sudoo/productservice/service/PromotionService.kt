package com.sudoo.productservice.service

import com.sudoo.productservice.dto.PatchAmountPromotionDto
import com.sudoo.productservice.dto.PromotionDto

interface PromotionService {
    suspend fun getAllPromotion(enable: Boolean?): List<PromotionDto>

    suspend fun getPromotion(promotionId: String): PromotionDto

    suspend fun upsertPromotion(promotion: PromotionDto): PromotionDto

    suspend fun deletePromotion(promotionId: String)

    suspend fun patchPromotion(patchPromotion: PatchAmountPromotionDto): PatchAmountPromotionDto
}