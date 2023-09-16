package com.sudoo.domain.exception

import org.springframework.http.HttpStatus

class CommonException(message: String? = null) :
    ApiException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, message ?: "Something went wrong")