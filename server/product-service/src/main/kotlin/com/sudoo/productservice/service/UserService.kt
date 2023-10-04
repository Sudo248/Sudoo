package com.sudoo.productservice.service

import com.sudoo.productservice.dto.UserInfoDto

interface UserService {
    suspend fun getUserInfo(userId: String): UserInfoDto
}