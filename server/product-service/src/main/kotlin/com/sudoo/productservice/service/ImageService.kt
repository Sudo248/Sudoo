package com.sudoo.productservice.service

import com.sudoo.productservice.dto.ImageDto

interface ImageService {
    suspend fun addImageToProduct(imageDto: ImageDto): ImageDto
    suspend fun deleteImage(imageId: String): String
}