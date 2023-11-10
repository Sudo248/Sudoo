package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.mapper.toImage
import com.sudoo.productservice.mapper.toImageDto
import com.sudoo.productservice.repository.ImageRepository
import com.sudoo.productservice.service.ImageService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ImageServiceImpl(
    private val imageRepository: ImageRepository,
) : ImageService {

    private val banner = "banner"

    override suspend fun getImageByOwnerId(ownerId: String): List<ImageDto> {
        return imageRepository.getAllByOwnerId(ownerId).map {
            it.toImageDto()
        }.toList()
    }

    override suspend fun getImageById(imageId: String): ImageDto {
        val image = imageRepository.findById(imageId) ?: throw NotFoundException("Not found image $imageId")
        return image.toImageDto()
    }

    override suspend fun upsertImage(imageDto: ImageDto): ImageDto {
        val image = imageDto.toImage()
        imageRepository.save(image)
        return image.toImageDto()
    }

    override suspend fun deleteImage(imageId: String): ImageDto {
        val image = imageRepository.findById(imageId) ?: throw NotFoundException("Not found image $imageId")
        imageRepository.delete(image)
        return image.toImageDto()
    }

    override suspend fun getBanners(): List<ImageDto> {
        return imageRepository.getAllByOwnerId(banner).map { it.toImageDto() }.toList()
    }

    override suspend fun upsertBanner(imageDto: ImageDto): ImageDto {
        val image = imageDto.toImage().copy(ownerId = banner)
        imageRepository.save(image)
        return image.toImageDto()
    }
}