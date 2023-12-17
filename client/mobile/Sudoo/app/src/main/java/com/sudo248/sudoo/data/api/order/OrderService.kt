package com.sudo248.sudoo.data.api.order

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.order.OrderDto
import com.sudo248.sudoo.data.dto.order.PatchOrderSupplierDto
import com.sudo248.sudoo.data.dto.order.UpsertOrderDto
import com.sudo248.sudoo.data.dto.order.UpsertOrderPromotionDto
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.order.OrderSupplierInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface OrderService {

    @POST("orders")
    suspend fun createOrder(@Body upsertOrderDto: UpsertOrderDto): Response<BaseResponse<OrderDto>>

    @GET("orders/{orderId}")
    suspend fun getOrderById(@Path("orderId") orderId: String): Response<BaseResponse<OrderDto>>

    @PATCH("orders/{orderId}/promotion")
    suspend fun updatePromotion(
        @Path("orderId") orderId: String,
        @Body upsertOrderPromotionDto: UpsertOrderPromotionDto
    ): Response<BaseResponse<UpsertOrderPromotionDto>>

    @DELETE("orders/{orderId}/cancel")
    suspend fun cancelOrderById(@Path("orderId") orderId: String): Response<BaseResponse<*>>

    @GET("orders/order-supplier/users")
    suspend fun getListOrderSupplierByStatus(@Query("status") status: String): Response<BaseResponse<List<OrderSupplierInfo>>>

    @GET("orders/order-supplier/{orderSupplierId}")
    suspend fun getOrderSupplierDetail(@Path("orderSupplierId") orderSupplierId: String): Response<BaseResponse<OrderDto>>

    @PATCH("orders/order-supplier/{orderSupplierId}")
    suspend fun patchOrderSupplier(
        @Path("orderSupplierId") orderSupplierId: String,
        @Body patchOrderSupplier: PatchOrderSupplierDto
    ): Response<BaseResponse<*>>
}