package com.sudoo.authservice.exception

import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class RefreshTokenExpired(message: String? = null) : ApiException(HttpStatus.BAD_REQUEST, message ?: "Refresh token is expired")