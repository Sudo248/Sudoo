package com.sudoo.authservice.exception

import com.sudoo.domain.common.SudooError
import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class WrongPasswordException : ApiException(HttpStatus.BAD_REQUEST, SudooError.WRONG_PASSWORD.message)