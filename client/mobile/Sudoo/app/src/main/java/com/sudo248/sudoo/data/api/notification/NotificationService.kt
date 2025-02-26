package com.sudo248.sudoo.data.api.notification

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.notification.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL)
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface NotificationService {

    @POST("notification/token/{token}")
    suspend fun saveToken(@Path("token") token: String): Response<BaseResponse<Boolean>>

    @POST("notification/send/promotion")
    suspend fun sendNotificationPromotion(@Body notification: Notification): Response<BaseResponse<String>>
}