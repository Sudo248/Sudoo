package com.sudoo.authservice.controller

import com.sudoo.authservice.dto.ChangePasswordDto
import com.sudoo.authservice.dto.SignInDto
import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.service.AccountService
import com.sudoo.authservice.service.AuthConfigService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AccountController(
    private val authConfigService: AuthConfigService,
    private val accountService: AccountService
) : BaseController() {

    @GetMapping("/internal/accounts/role")
    suspend fun getRole(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
    ): ResponseEntity<BaseResponse<*>> = handle {
        accountService.getRole(userId)
    }

    @GetMapping("/config")
    suspend fun getAuthConfig(): ResponseEntity<BaseResponse<*>> = handle {
        authConfigService.getConfig()
    }

    @PostMapping("/sign-in")
    suspend fun signIn(@RequestBody body: SignInDto): ResponseEntity<BaseResponse<*>> = handle {
        accountService.signIn(body)
    }

    @PostMapping("/sign-up")
    suspend fun signUp(@RequestBody body: SignUpDto): ResponseEntity<BaseResponse<*>> = handle {
        accountService.signUp(body)
    }

    @PostMapping("/change-password")
    suspend fun changePassword(
        @RequestHeader(Constants.HEADER_USER_ID) userId: String,
        @RequestBody changePasswordDto: ChangePasswordDto
    ): ResponseEntity<BaseResponse<*>> = handle {
        accountService.changePassword(userId, changePasswordDto)
    }

    @GetMapping("/logout")
    suspend fun logout(@RequestHeader(Constants.HEADER_USER_ID) userId: String
    ): ResponseEntity<BaseResponse<*>> = handle {
            accountService.logout(userId)
        }
}