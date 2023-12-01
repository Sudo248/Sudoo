package com.sudoo.shipment.api

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudoo.shipment.BuildConfig
import com.sudoo.shipment.Constants
import com.sudoo.shipment.api.request.AccountRequest
import com.sudoo.shipment.model.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

@ApiService(baseUrl = BuildConfig.BASE_URL + "auth/")
@EnableAuthentication(Constants.TOKEN)
@LoggingLever(level = Level.BODY)
interface AuthService {
    @POST("sign-in")
    suspend fun signIn(@Body accountRequest: AccountRequest): Response<BaseResponse<TokenDto>>
}