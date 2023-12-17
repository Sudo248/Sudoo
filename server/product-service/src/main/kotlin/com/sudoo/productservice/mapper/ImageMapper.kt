package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.dto.UpsertImageDto
import com.sudoo.productservice.dto.UpsertProductDto
import com.sudoo.productservice.model.Image

fun ImageDto.toImage(): Image {
    return Image (
        imageId = IdentifyCreator.createOrElse(imageId),
        ownerId = ownerId,
        url = url,
    ).also {
        it.isNewImage = imageId.isNullOrEmpty()
    }
}

fun Image.toImageDto(): ImageDto {
    return ImageDto(
        imageId = imageId,
        ownerId = ownerId,
        url = url,
    )
}

fun UpsertImageDto.toImage(ownerId: String): Image {
    return Image (
            imageId = IdentifyCreator.createOrElse(imageId),
            ownerId = ownerId,
            url = url,
    ).also {
        it.isNewImage = imageId.isNullOrEmpty()
    }
}