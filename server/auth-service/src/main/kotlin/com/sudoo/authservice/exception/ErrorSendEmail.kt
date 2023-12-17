package com.sudoo.authservice.exception

import com.sudoo.domain.common.SudooError
import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class ErrorSendEmail : ApiException(HttpStatus.BAD_REQUEST, "Error when send email!")