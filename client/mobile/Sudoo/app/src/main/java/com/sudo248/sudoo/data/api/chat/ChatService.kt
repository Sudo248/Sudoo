package com.sudo248.sudoo.data.api.chat

import com.sudo248.base_android_annotation.apiservice.ApiService
import com.sudo248.base_android_annotation.apiservice.EnableAuthentication
import com.sudo248.base_android_annotation.apiservice.logging_level.Level
import com.sudo248.base_android_annotation.apiservice.logging_level.LoggingLever
import com.sudo248.sudoo.BuildConfig
import com.sudo248.sudoo.data.api.BaseResponse
import com.sudo248.sudoo.data.dto.chat.ChatDto
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.chat.Chat
import com.sudo248.sudoo.domain.entity.chat.Conversation
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@ApiService(baseUrl = BuildConfig.BASE_URL + "chat/")
@EnableAuthentication(Constants.Key.TOKEN)
@LoggingLever(level = Level.BODY)
interface ChatService {

    @GET("conversation/{topic}")
    suspend fun getConversation(@Path("topic") topic: String): Response<BaseResponse<Conversation>>

    @POST("conversation/{topic}/send")
    suspend fun sendMessageToTopic(@Path("topic") topic: String,@Body chatDto: ChatDto): Response<BaseResponse<Chat>>
}