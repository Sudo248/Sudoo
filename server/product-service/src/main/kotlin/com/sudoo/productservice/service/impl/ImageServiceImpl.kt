package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.mapper.toImage
import com.sudoo.productservice.mapper.toImageDto
import com.sudoo.productservice.repository.ImageRepository
import com.sudoo.productservice.service.ImageService
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl(
    private val imageRepository: ImageRepository,
) : ImageService {
    override suspend fun addImageToProduct(imageDto: ImageDto): ImageDto {
        val image = imageDto.toImage()
        imageRepository.save(image)
        return image.toImageDto()
    }

    override suspend fun deleteImage(imageId: String): String {
        val image = imageRepository.findById(imageId) ?: throw NotFoundException("Not found image $imageId")
        imageRepository.delete(image)
        return imageId
    }
}