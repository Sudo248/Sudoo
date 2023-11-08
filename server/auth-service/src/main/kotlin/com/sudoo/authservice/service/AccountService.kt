package com.sudoo.authservice.service

import com.sudoo.authservice.dto.ChangePasswordDto
import com.sudoo.authservice.dto.SignInDto
import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.dto.TokenDto

interface AccountService {
    suspend fun signIn(signInDto: SignInDto): TokenDto
    suspend fun signUp(signUpDto: SignUpDto)
    suspend fun changePassword(userId: String, changePasswordDto: ChangePasswordDto): Boolean
    suspend fun logout(userId: String): Boolean

    suspend fun registerAdmin(signUpDto: SignUpDto): Boolean
}