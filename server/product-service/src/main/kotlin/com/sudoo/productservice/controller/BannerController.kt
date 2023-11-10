package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.productservice.dto.ImageDto
import com.sudoo.productservice.service.ImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/discovery/banners")
class BannerController(
    private val imageService: ImageService
) : BaseController() {

    @GetMapping
    suspend fun getBanners(): ResponseEntity<BaseResponse<*>> = handle {
        imageService.getBanners()
    }

    @PostMapping
    suspend fun upsertBanner(
        @RequestBody banner: ImageDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.upsertBanner(banner)
    }

    @DeleteMapping("/{imageId}")
    suspend fun deleteBanner(
        @PathVariable("imageId") imageId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        imageService.deleteImage(imageId)
    }
}