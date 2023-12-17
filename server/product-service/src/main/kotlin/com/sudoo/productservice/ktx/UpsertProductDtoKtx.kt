package com.sudoo.productservice.ktx

import com.sudoo.domain.exception.BadRequestException
import com.sudoo.productservice.dto.UpsertProductDto

fun UpsertProductDto.validate(): Boolean {
    return if (
        name != null &&
        description != null &&
        listedPrice != null &&
        amount != null
    ) {
        true
    } else {
        throw BadRequestException("Missing value")
    }
}