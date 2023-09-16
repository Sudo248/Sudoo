package com.sudoo.authservice.exception

import com.sudoo.domain.common.SudooError
import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class EmailOrPhoneNumberNotValidatedException : ApiException(HttpStatus.BAD_REQUEST, SudooError.EMAIL_OR_PHONE_NUMBER_NOT_VALIDATED.message)