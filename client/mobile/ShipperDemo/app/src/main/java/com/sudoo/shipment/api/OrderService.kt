package com.sudoo.shipment.api

import com.google.android.gms.common.api.Response
import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever

import com.sudoo.shipment.Constants
import retrofit2.http.GET
import retrofit2.http.Path

//@ApiService(baseUrl = BuildC)
@EnableAuthentication(Constants.TOKEN)
@LoggingLever(level = Level.BODY)
interface OrderService {
    @GET("orders/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: String): Response<BaseResponse<OrderDto>>
}