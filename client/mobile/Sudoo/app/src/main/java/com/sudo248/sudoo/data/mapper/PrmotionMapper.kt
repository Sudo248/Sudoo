package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.domain.entity.promotion.Promotion

fun PromotionDto.toPromotion(): Promotion {
    return Promotion(
        promotionId, value, name
    )
}