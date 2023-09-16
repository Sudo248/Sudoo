package com.sudoo.authservice.exception

import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class UserException : ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't create user")