package com.sudoo.authservice.controller

import com.sudoo.authservice.utils.TokenUtils
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.SudooController
import com.sudoo.domain.common.Constants
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController(
    private val tokenUtils: TokenUtils
) : BaseController by SudooController() {

    @GetMapping("/refresh-token")
    suspend fun refreshToken(@RequestHeader(Constants.HEADER_USER_ID) userId: String) = handle{

    }

}