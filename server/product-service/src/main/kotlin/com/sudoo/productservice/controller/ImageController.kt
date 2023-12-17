package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.service.ImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/discovery/images")
class ImageController(
    private val imageService: ImageService
) : BaseController() {

    @PostMapping
    suspend fun upsertImage(
        @RequestBody imageDto: ImageDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.upsertImage(imageDto)
    }

    @DeleteMapping("/{imageId}")
    suspend fun deleteImage(
        @PathVariable("imageId") imageId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.deleteImage(imageId)
    }
}