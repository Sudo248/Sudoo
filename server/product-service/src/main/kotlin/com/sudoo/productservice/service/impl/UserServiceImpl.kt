package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.BaseResponse
import com.sudoo.productservice.dto.UserInfoDto
import com.sudoo.productservice.exception.UserException
import com.sudoo.productservice.service.UserService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class UserServiceImpl(
    @Qualifier("user-service") private val client: WebClient
) : UserService {
    override suspend fun getUserInfo(): UserInfoDto {
        val response = client.get()
            .uri("/short-info")
            .retrieve()
            .awaitBodyOrNull<ResponseEntity<BaseResponse<UserInfoDto>>>() ?: throw UserException()
        if (response.statusCode != HttpStatus.OK || !response.hasBody()) {
            throw UserException()
        }
        return response.body!!.data ?: throw UserException()
    }
}