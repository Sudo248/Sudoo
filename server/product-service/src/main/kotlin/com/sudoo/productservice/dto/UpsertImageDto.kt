package com.sudoo.productservice.dto

data class UpsertImageDto(
        val imageId: String?,
        val ownerId: String?,
        val url: String,
)