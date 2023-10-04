package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import com.sudoo.productservice.dto.UserInfoDto
import com.sudoo.productservice.exception.UserException
import com.sudoo.productservice.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class UserServiceImpl(
    @Qualifier("user-service") private val client: WebClient
) : UserService {
    override suspend fun getUserInfo(userId: String): UserInfoDto {
        val response = client.get()
            .uri("/info")
            .header(Constants.HEADER_USER_ID, userId)
            .retrieve()
            .awaitBodyOrNull<BaseResponse<UserInfoDto>>() ?: throw UserException()

        return response.data ?: throw UserException()
    }
}