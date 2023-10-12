package com.sudo248.sudoo.data.api.discovery

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.discovery.CategoryDto
import com.sudo248.sudoo.data.dto.discovery.CategoryInfoDto
import com.sudo248.sudoo.data.dto.discovery.ProductDto
import com.sudo248.sudoo.data.dto.user.AddressDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@ApiService(baseUrl = BuildConfig.BASE_URL + "discovery/")
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface DiscoveryService {
    @GET("category/info")
    suspend fun getAllCategoryInfo(): Response<BaseResponse<List<CategoryInfoDto>>>

    @GET("category")
    suspend fun getAllCategory(
        @Query("location") location: String = Constants.location
    ): Response<BaseResponse<List<CategoryDto>>>

    @GET("category/{categoryId}")
    suspend fun getCategoryById(
        @Path("categoryId") categoryId: String,
        @Query("location") location: String = Constants.location
    ): Response<BaseResponse<CategoryDto>>

    @GET("product/{productId}")
    suspend fun getProductById(
        @Path("productId") productId: String,
        @Query("location") location: String = Constants.location
    ): Response<BaseResponse<ProductDto>>

    @GET("supplier/{supplierId}/address")
    suspend fun getSupplierAddress(
        @Path("supplierId") supplierId: String
    ): Response<BaseResponse<AddressDto>>

    @GET("product/search/{productName}")
    suspend fun searchProductByName(
        @Path("productName") productName: String
    ): Response<BaseResponse<List<ProductDto>>>
}