package com.sudoo.authservice.controller

import com.sudoo.authservice.service.TokenService
import com.sudoo.authservice.utils.TokenUtils
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.common.Constants
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
    private val tokenService: TokenService
) : BaseController() {

    @GetMapping("/refresh-token")
    suspend fun refreshToken(@RequestHeader(Constants.REFRESH_TOKEN) refreshToken: String) = handle {
        tokenService.refreshToken(refreshToken)
    }

}