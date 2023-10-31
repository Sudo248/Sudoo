package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.productservice.dto.PatchAmountPromotionDto
import com.sudoo.productservice.dto.PromotionDto
import com.sudoo.productservice.service.PromotionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/promotions")
class PromotionController (
    private val promotionService: PromotionService
) : BaseController() {

    @GetMapping
    suspend fun getAllPromotion() : ResponseEntity<BaseResponse<*>> = handle {
        promotionService.getAllPromotion()
    }

    @GetMapping("/{promotionId}")
    suspend fun getPromotion(
        @PathVariable("promotionId") promotionId: String
    ) : ResponseEntity<BaseResponse<*>> = handle {
        promotionService.getPromotion(promotionId)
    }

    @PostMapping
    suspend fun upsertPromotion(
        @RequestBody promotionDto: PromotionDto
    ) : ResponseEntity<BaseResponse<*>> = handle {
        promotionService.upsertPromotion(promotionDto)
    }

    @DeleteMapping("/{promotionId}")
    suspend fun deletePromotion(
        @PathVariable("promotionId") promotionId: String
    ) : ResponseEntity<BaseResponse<*>> = handle {
        promotionService.deletePromotion(promotionId)
    }

    @PatchMapping("/internal/amount")
    suspend fun patchPromotion(
        @RequestBody promotion: PatchAmountPromotionDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        promotionService.patchPromotion(promotion)
    }
}