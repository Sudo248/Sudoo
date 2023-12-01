package com.sudo248.sudoo.data.api.discovery

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.discovery.CategoryDto
import com.sudo248.sudoo.data.dto.discovery.CommentListDto
import com.sudo248.sudoo.data.dto.discovery.ImageDto
import com.sudo248.sudoo.data.dto.discovery.ProductDto
import com.sudo248.sudoo.data.dto.discovery.ProductInfoDto
import com.sudo248.sudoo.data.dto.discovery.ProductListDto
import com.sudo248.sudoo.data.dto.discovery.ReviewDto
import com.sudo248.sudoo.data.dto.discovery.ReviewListDto
import com.sudo248.sudoo.data.dto.discovery.SupplierDto
import com.sudo248.sudoo.data.dto.discovery.UpsertReviewDto
import com.sudo248.sudoo.domain.common.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("products")
    suspend fun getProductList(
        @Query("categoryId") categoryId: String,
        @Query("name") name: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT,
    ): Response<BaseResponse<ProductListDto>>

    @GET("products/recommend")
    suspend fun getRecommendProductList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT,
    ): Response<BaseResponse<ProductListDto>>

    @GET("products/{productId}")
    suspend fun getProductDetail(
        @Path("productId") productId: String
    ): Response<BaseResponse<ProductDto>>

    @GET("products/{productId}/info")
    suspend fun getProductInfo(
        @Path("productId") productId: String
    ): Response<BaseResponse<ProductInfoDto>>

    @GET("products/search/{productName}")
    suspend fun searchProductByName(
        @Path("productName") productName: String
    ): Response<BaseResponse<ProductListDto>>

    @GET("suppliers/{supplierId}")
    suspend fun getSupplierDetail(
        @Path("supplierId") supplierId: String
    ): Response<BaseResponse<SupplierDto>>

    @GET("comments")
    suspend fun getCommentsOfProduct(
        @Query("productId") productId: String,
        @Query("isReviewed") isReviewed: Boolean = true,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT,
    ): Response<BaseResponse<CommentListDto>>

    @GET("reviews")
    suspend fun getReviews(
        @Query("isReviewed") isReviewed: Boolean,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT,
    ): Response<BaseResponse<ReviewListDto>>

    @POST("reviews")
    suspend fun upsertReview(
        @Body upsertReview: UpsertReviewDto
    ): Response<BaseResponse<ReviewDto>>

    @DELETE("comments/{commentId}")
    suspend fun deleteComment(
        @Path("commentId") commentId: String
    ): Response<BaseResponse<Any>>

    @GET("banners")
    suspend fun getBanner(): Response<BaseResponse<List<ImageDto>>>
}