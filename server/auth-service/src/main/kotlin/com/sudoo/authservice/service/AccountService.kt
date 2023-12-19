package com.sudoo.authservice.service

import com.sudoo.authservice.dto.ChangePasswordDto
import com.sudoo.authservice.dto.SignInDto
import com.sudoo.authservice.dto.SignUpDto
import com.sudoo.authservice.dto.TokenDto
import com.sudoo.authservice.model.Role

interface AccountService {

    suspend fun getRole(userId: String): Role
    suspend fun signIn(signInDto: SignInDto): TokenDto
    suspend fun signUp(signUpDto: SignUpDto)
    suspend fun changePassword(userId: String, changePasswordDto: ChangePasswordDto): Boolean
    suspend fun logout(userId: String): Boolean

    suspend fun registerAdmin(signUpDto: SignUpDto): Boolean
}