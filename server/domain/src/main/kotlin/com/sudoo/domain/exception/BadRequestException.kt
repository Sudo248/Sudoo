package com.sudoo.domain.exception

import org.springframework.http.HttpStatus

class BadRequestException(message: String? = null) : ApiException(HttpStatus.BAD_REQUEST, message ?: "Bad request")