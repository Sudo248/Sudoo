package com.sudo248.sudoo.data.api.image

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.image.ImageDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

@ApiService(baseUrl = BuildConfig.BASE_URL + "storage/images/")
@LoggingLever(level = Level.BODY)
interface ImageService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<BaseResponse<ImageDto>>

}