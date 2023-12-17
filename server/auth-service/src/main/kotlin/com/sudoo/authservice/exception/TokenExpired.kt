package com.sudoo.authservice.exception

import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class TokenExpired(message: String? = null) : ApiException(HttpStatus.BAD_REQUEST, message ?: "Token is expired")