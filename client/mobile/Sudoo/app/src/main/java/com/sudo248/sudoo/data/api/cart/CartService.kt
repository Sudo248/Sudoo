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
    @POST("product")
    suspend fun addProductToCart(@Body addCartProductDto: AddCartProductDto): Response<BaseResponse<CartDto>>

    @PUT("{cartId}/item")
    suspend fun updateSupplierProduct(
        @Path("cartId") cartId: String,
        @Body addCartProductDto: List<AddCartProductDto>
    ): Response<BaseResponse<CartDto>>

    @DELETE("{cartId}/item")
    suspend fun deleteSupplierProduct(
        @Path("cartId") cartId: String,
        @Query("supplierId") supplierId: String,
        @Query("productId") productId: String
    ): Response<BaseResponse<CartDto>>

    @GET("active/count-item")
    suspend fun countItemInCart(): Response<BaseResponse<Int>>

    //------------------------------------
    @GET("active")
    suspend fun getActiveCart():Response<BaseResponse<CartDto>>

    @GET("/api/v1/carts/")
    suspend fun getCartByStatus():Response<BaseResponse<CartDto>>

    @POST("product")
    suspend fun updateProductToActiveCart(@Body upsertCartProductDto: AddCartProductDto):Response<BaseResponse<CartDto>>

    @POST("processing")
    suspend fun createProcessingCartWithProduct(@Body cartProductsDto: CartProductsDto):Response<BaseResponse<CartDto>>
}