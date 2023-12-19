package com.sudoo.productservice.controller

import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.productservice.dto.PatchAmountPromotionDto
import com.sudoo.productservice.dto.PromotionDto
import com.sudoo.productservice.service.PromotionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/promotions")
class PromotionController (
    private val promotionService: PromotionService
) : BaseController() {

    @GetMapping
    suspend fun getAllPromotion(
        @RequestParam("enable", required =  false) enable: Boolean?,
    ) : ResponseEntity<BaseResponse<*>> = handle {
        promotionService.getAllPromotion(enable)
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

    @PutMapping("/internal/amount")
    suspend fun patchPromotion(
        @RequestBody promotion: PatchAmountPromotionDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        promotionService.patchPromotion(promotion)
    }
}