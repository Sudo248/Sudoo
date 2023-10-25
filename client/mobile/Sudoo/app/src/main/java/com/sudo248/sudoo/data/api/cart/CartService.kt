package com.sudo248.sudoo.data.api.cart

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.cart.AddSupplierProductDto
import com.sudo248.sudoo.data.dto.cart.CartDto
import com.sudo248.sudoo.data.dto.cart.CartProductsDto
import com.sudo248.sudoo.data.dto.cart.UpsertCartProductDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.*

@ApiService(baseUrl = BuildConfig.BASE_URL + "cart/")
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface CartService {
    @POST("/api/v1/cart/item")
    suspend fun addSupplierProduct(@Body addSupplierProductDto: AddSupplierProductDto): Response<BaseResponse<CartDto>>

    @PUT("/api/v1/cart/{cartId}/item")
    suspend fun updateSupplierProduct(
        @Path("cartId") cartId: String,
        @Body addSupplierProductDto: List<AddSupplierProductDto>
    ): Response<BaseResponse<CartDto>>

    @DELETE("/api/v1/cart/{cartId}/item")
    suspend fun deleteSupplierProduct(
        @Path("cartId") cartId: String,
        @Query("supplierId") supplierId: String,
        @Query("productId") productId: String
    ): Response<BaseResponse<CartDto>>

    @GET("/api/v1/cart/active")
    suspend fun getCart(): Response<BaseResponse<CartDto>>

    @GET("/api/v1/cart/active/count-item")
    suspend fun getItemInCart(): Response<BaseResponse<Int>>

    //------------------------------------
    @GET("/api/v1/carts/active")
    suspend fun getActiveCart():Response<BaseResponse<CartDto>>

    @GET("/api/v1/carts/")
    suspend fun getCartByStatus():Response<BaseResponse<CartDto>>

    @POST("/api/v1/carts/product")
    suspend fun updateProductToActiveCart(@Body upsertCartProductDto: AddSupplierProductDto):Response<BaseResponse<CartDto>>

    @POST("/api/v1/carts/processing/")
    suspend fun createProcessingCartWithProduct(@Body cartProductsDto: CartProductsDto):Response<BaseResponse<CartDto>>
}