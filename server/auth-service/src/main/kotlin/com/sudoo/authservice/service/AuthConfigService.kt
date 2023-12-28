package com.sudoo.authservice.service

import com.sudoo.authservice.dto.AuthConfigDto

interface AuthConfigService {
    suspend fun getConfig(): AuthConfigDto
}