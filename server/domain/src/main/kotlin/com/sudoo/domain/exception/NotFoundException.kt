package com.sudoo.domain.exception

import org.springframework.http.HttpStatus

class NotFoundException(message: String? = null) : ApiException(HttpStatus.NOT_FOUND, message ?: "Not found")