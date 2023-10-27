package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.UserInfoDto
import com.sudoo.productservice.dto.UserProductDto
import com.sudoo.productservice.model.UserProduct
import java.time.LocalDateTime

fun UserProductDto.toUserProduct(userId: String): UserProduct {
    return UserProduct(
        userProductId = IdentifyCreator.createOrElse(userProductId),
        productId = productId,
        userId = userId,
        rate = rate,
        isReviewed = isReviewed,
        comment = comment,
    ).also {
        it.isNewUserProduct = userProductId.isNullOrEmpty()
    }
}

fun UserProduct.toUserProductDto(userInfo: UserInfoDto? = null): UserProductDto {
    return UserProductDto(
        userProductId = userProductId,
        productId = productId,
        rate = rate,
        isReviewed = isReviewed,
        comment = comment,
        updatedAt = updatedAt,
        createdAt = createdAt,
        userInfo = userInfo,
        images = images?.map { it.url }
    )
}