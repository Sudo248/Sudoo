package com.sudoo.productservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UserProductEmbededDto(
    @JsonProperty("user_id")
    val userId: String,
    val dob: LocalDate,
    val gender: Gender,
    @JsonProperty("product_id")
    val productId: String,
    val categories: List<String>
)
