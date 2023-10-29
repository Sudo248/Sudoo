package com.sudo248.sudoo.data.api.order

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.order.UpsertOrderDto
import com.sudo248.sudoo.data.dto.order.OrderDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface OrderService {

    @POST("orders")
    suspend fun createOrder(@Body upsertOrderDto: UpsertOrderDto): Response<BaseResponse<UpsertOrderDto>>

    @GET("orders/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: String): Response<BaseResponse<OrderDto>>

    @PATCH("orders/{orderId}/update/promotion/{promotionId}")
    suspend fun updatePromotion(
        @Path("orderId") orderId: String,
        @Path("promotionId") promotionId: String
    ): Response<BaseResponse<OrderDto>>

}