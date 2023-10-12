package com.sudo248.sudoo.data.api.promotion

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.promotion.PromotionDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface PromotionService {

    @GET("promotion/")
    suspend fun getAllPromotion(): Response<BaseResponse<List<PromotionDto>>>

    @GET("promotion/{promotionId}")
    suspend fun getPromotionById(@Path("promotionId") promotionId: String): Response<BaseResponse<PromotionDto>>
}