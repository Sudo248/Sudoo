package com.sudoo.shipment.api

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudoo.shipment.BuildConfig

import com.sudoo.shipment.Constants
import com.sudoo.shipment.model.Order
import com.sudoo.shipment.model.PatchOrderSupplierDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.TOKEN)
@LoggingLever(level = Level.BODY)
interface OrderService {
    @GET("orders/order-supplier/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: String): Response<BaseResponse<Order>>

    @PATCH("orders/order-supplier/{orderSupplierId}")
    suspend fun patchOrderSupplier(
        @Path("orderSupplierId") orderSupplierId: String,
        @Body patchOrderSupplier: PatchOrderSupplierDto
    ): Response<BaseResponse<*>>
}