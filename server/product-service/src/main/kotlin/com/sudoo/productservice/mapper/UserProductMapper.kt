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
        isLike = isLike,
        comment = comment,
        createAt = createAt ?: LocalDateTime.now(),
    ).also {
        it.isNewUserProduct = userProductId.isNullOrEmpty()
    }
}

fun UserProduct.toUserProductDto(userInfo: UserInfoDto? = null): UserProductDto {
    return UserProductDto(
        userProductId = userProductId,
        productId = productId,
        rate = rate,
        isLike = isLike,
        comment = comment,
        createAt = createAt,
        userInfo = userInfo,
        images = images?.map { it.url }
    )
}