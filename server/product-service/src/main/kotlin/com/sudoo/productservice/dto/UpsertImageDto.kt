package com.sudoo.productservice.dto

data class UpsertImageDto(
        var imageId: String?,
        var ownerId: String?,
        val url: String,
)