package com.sudoo.authservice.controller

import com.sudoo.authservice.controller.dto.ChangePasswordDto
import com.sudoo.authservice.controller.dto.SignInDto
import com.sudoo.authservice.controller.dto.SignUpDto
import com.sudoo.authservice.service.AccountService
import com.sudoo.domain.base.BaseController
import com.sudoo.domain.base.BaseResponse
import com.sudoo.domain.common.Constants
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AccountController(
    private val accountService: AccountService
) : BaseController() {

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
    suspend fun logout(@RequestHeader(Constants.HEADER_USER_ID) userId: String): ResponseEntity<BaseResponse<*>> =
        handle {
            accountService.logout(userId)
        }
}