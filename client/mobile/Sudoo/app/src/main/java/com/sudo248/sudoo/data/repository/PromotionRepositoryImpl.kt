package com.sudo248.sudoo.data.repository

import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.data.api.handleResponse
import com.sudo248.base_android.ktx.stateOn
import com.sudo248.sudoo.data.api.promotion.PromotionService
import com.sudo248.sudoo.data.ktx.data
import com.sudo248.sudoo.data.ktx.errorBody
import com.sudo248.sudoo.data.mapper.toAddSupplierProductDto
import com.sudo248.sudoo.data.mapper.toCart
import com.sudo248.sudoo.data.mapper.toPromotion
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.repository.PromotionRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromotionRepositoryImpl @Inject constructor(
    private val promotionService: PromotionService,
    private val ioDispatcher: CoroutineDispatcher,
) : PromotionRepository {
    override suspend fun getAllPromotion(): DataState<List<Promotion>, Exception> = stateOn(ioDispatcher){
        val response = handleResponse(promotionService.getAllPromotion())
        if (response.isSuccess) {
            response.data().map { it.toPromotion() }
        } else {
            throw response.error().errorBody()
        }
    }

    override suspend fun getPromotionById(promotionId: String): DataState<Promotion, Exception> = stateOn(ioDispatcher){
        val response = handleResponse(promotionService.getPromotionById(promotionId))
        if (response.isSuccess) {
            response.data().toPromotion()
        } else {
            throw response.error().errorBody()
        }
    }
}