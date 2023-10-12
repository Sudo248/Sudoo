package com.sudo248.sudoo.data.api.payment

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.api.payment.request.PaymentRequest
import com.sudo248.sudoo.data.dto.payment.PaymentDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL + "payment/")
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface PaymentService {

    @POST("pay/{currentTime}")
    suspend fun pay(@Path("currentTime") currentTime: Long, @Body request: PaymentRequest): Response<BaseResponse<PaymentDto>>
}