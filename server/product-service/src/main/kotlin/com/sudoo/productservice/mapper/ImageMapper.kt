package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.model.Image

fun ImageDto.toImage(): Image {
    return Image (
        imageId = IdentifyCreator.createOrElse(imageId),
        ownerId = ownerId,
        url = url,
    )
}

fun Image.toImageDto(): ImageDto {
    return ImageDto(
        imageId = imageId,
        ownerId = ownerId,
        url = url,
    )
}