package com.sudoo.authservice.service.impl

import com.sudoo.authservice.exception.UserException
import com.sudoo.authservice.mapper.toUserDto
import com.sudoo.authservice.model.Account
import com.sudoo.authservice.service.UserService
import com.sudoo.domain.base.BaseResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class UserServiceImpl(
    @Qualifier("user-service") private val client: WebClient
) : UserService {

    override suspend fun createUserForAccount(account: Account): Boolean {
        val userDto = account.toUserDto()

        client.post()
            .uri("/")
            .bodyValue(userDto)
            .retrieve()
            .awaitBodyOrNull<ResponseEntity<BaseResponse<*>>>() ?: throw UserException()

        return true
    }
}