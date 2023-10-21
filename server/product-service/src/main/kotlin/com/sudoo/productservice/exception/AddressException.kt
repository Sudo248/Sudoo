package com.sudoo.productservice.exception

import com.sudoo.domain.exception.ApiException
import org.springframework.http.HttpStatus

class AddressException : ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Not found address")