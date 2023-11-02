package com.sudo248.sudoo.data.api.storage

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.image.ImageDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

@ApiService(baseUrl = BuildConfig.BASE_URL + "storage/")
@LoggingLever(level = Level.BODY)
interface StorageService {
    @Multipart
    @POST("images/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<BaseResponse<ImageDto>>

    @Streaming
    @GET("files/download/{fileName}")
    suspend fun downloadFile(@Path("fileName") fileName: String): Response<ResponseBody>

}