package com.sudoo.authservice.service

import com.sudoo.authservice.controller.dto.ChangePasswordDto
import com.sudoo.authservice.controller.dto.SignInDto
import com.sudoo.authservice.controller.dto.SignUpDto
import com.sudoo.authservice.controller.dto.TokenDto

interface AccountService {
    suspend fun signIn(signInDto: SignInDto): TokenDto
    suspend fun signUp(signUpDto: SignUpDto)
    suspend fun changePassword(userId: String, changePasswordDto: ChangePasswordDto): Boolean
    suspend fun logout(userId: String): Boolean
}