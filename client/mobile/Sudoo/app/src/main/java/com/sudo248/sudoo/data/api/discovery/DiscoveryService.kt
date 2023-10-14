package com.sudo248.sudoo.data.api.discovery

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.discovery.CategoryDto
import com.sudo248.sudoo.data.dto.discovery.ProductDto
import com.sudo248.sudoo.data.dto.discovery.ProductListDto
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
    @GET("categories")
    suspend fun getCategories(
        @Query("location") location: String = Constants.location
    ): Response<BaseResponse<List<CategoryDto>>>

    @GET("categories/{categoryId}")
    suspend fun getCategoryById(
        @Path("categoryId") categoryId: String
    ): Response<BaseResponse<CategoryDto>>

    @GET("categories/{categoryId}/products")
    suspend fun getProductListByCategoryId(
        @Path("categoryId") categoryId: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT,
    ): Response<BaseResponse<ProductListDto>>

    @GET("products/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId: String
    ): Response<BaseResponse<ProductDto>>

    @GET("supplier/{supplierId}/address")
    suspend fun getSupplierAddress(
        @Path("supplierId") supplierId: String
    ): Response<BaseResponse<AddressDto>>

    @GET("products/search/{productName}")
    suspend fun searchProductByName(
        @Path("productName") productName: String
    ): Response<BaseResponse<ProductListDto>>
}