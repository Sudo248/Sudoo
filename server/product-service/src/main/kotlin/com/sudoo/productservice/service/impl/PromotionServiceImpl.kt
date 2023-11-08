package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.BadRequestException
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.PatchAmountPromotionDto
import com.sudoo.productservice.dto.PromotionDto
import com.sudoo.productservice.mapper.toPromotion
import com.sudoo.productservice.mapper.toPromotionDto
import com.sudoo.productservice.repository.PromotionRepository
import com.sudoo.productservice.service.PromotionService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class PromotionServiceImpl(
    private val promotionRepository: PromotionRepository
) : PromotionService {
    override suspend fun getAllPromotion(enable: Boolean?): List<PromotionDto> {
        return enable?.let {
            promotionRepository.getAllByEnableAndTotalAmountGreaterThan(it, 0).map { promotion -> promotion.toPromotionDto() }.toList()
        } ?: promotionRepository.findAll().map { it.toPromotionDto() }.toList()
    }

    override suspend fun getPromotion(promotionId: String): PromotionDto {
        val promotion =
            promotionRepository.findById(promotionId) ?: throw NotFoundException("Not found promotion $promotionId")
        return promotion.toPromotionDto()
    }

    override suspend fun upsertPromotion(promotion: PromotionDto): PromotionDto {
        val savedPromotion = promotionRepository.save(promotion.toPromotion())
        return savedPromotion.toPromotionDto()
    }

    override suspend fun deletePromotion(promotionId: String) {
        val promotion =
            promotionRepository.findById(promotionId) ?: throw NotFoundException("Not found promotion $promotionId")
        promotionRepository.deleteById(promotionId)
    }

    override suspend fun patchPromotion(patchPromotion: PatchAmountPromotionDto): PatchAmountPromotionDto {
        val promotion = promotionRepository.findById(patchPromotion.promotionId)
            ?: throw NotFoundException("Not found promotion ${patchPromotion.promotionId}")
        if (patchPromotion.amount < 0 && abs(patchPromotion.amount) > promotion.totalAmount) {
            throw BadRequestException("Not enough promotion")
        }
        promotion.totalAmount += patchPromotion.amount
        promotionRepository.save(promotion)
        patchPromotion.amount = promotion.totalAmount
        return patchPromotion
    }
}