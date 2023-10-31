package com.sudoo.productservice.mapper

import com.sudoo.domain.common.Constants
import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.PromotionDto
import com.sudoo.productservice.model.Promotion

fun PromotionDto.toPromotion(): Promotion {
    return Promotion(
        promotionId = IdentifyCreator.createOrElse(promotionId),
        supplierId = supplierId ?: Constants.ADMIN_ID,
        name = name,
        value = value,
        totalAmount = totalAmount
    ).also {
        it.isNewPromotion = promotionId.isNullOrEmpty()
    }
}

fun Promotion.toPromotionDto(): PromotionDto {
    return PromotionDto(
        promotionId = promotionId,
        supplierId = supplierId,
        name = name,
        value = value,
        totalAmount = totalAmount
    )
}