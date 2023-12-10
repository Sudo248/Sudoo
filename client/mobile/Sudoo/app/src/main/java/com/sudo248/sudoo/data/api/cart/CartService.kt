package com.sudo248.sudoo.data.api.cart

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.cart.AddCartProductDto
import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.cart.CartProductsDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.*

@ApiService(baseUrl = BuildConfig.BASE_URL + "carts/")
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface CartService {
    @GET("active/count-item")
    suspend fun countItemInActiveCart(): Response<BaseResponse<Int>>

    //------------------------------------
    @GET("active")
    suspend fun getActiveCart():Response<BaseResponse<CartDto>>

    @POST("product")
    suspend fun updateProductToActiveCart(@Body upsertCartProductDto: AddCartProductDto):Response<BaseResponse<CartDto>>

    @POST("processing")
    suspend fun createProcessingCartWithProduct(@Body cartProductsDto: CartProductsDto):Response<BaseResponse<CartDto>>

    @DELETE("product")
    suspend fun deleteProductInCart(
        @Query("cartId") cartId: String,
        @Query("productId") productId: String
    ): Response<BaseResponse<CartDto>>
}