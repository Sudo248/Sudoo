package com.sudoo.productservice.service

import com.sudoo.productservice.dto.ImageDto

interface ImageService {
    suspend fun getImageByOwnerId(ownerId: String): List<ImageDto>
    suspend fun getImageById(imageId: String): ImageDto
    suspend fun upsertImage(imageDto: ImageDto): ImageDto
    suspend fun deleteImage(imageId: String): ImageDto
}