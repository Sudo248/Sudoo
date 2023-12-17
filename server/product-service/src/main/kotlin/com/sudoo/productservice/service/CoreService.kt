package com.sudoo.productservice.service

import com.sudoo.productservice.model.UserProduct

interface CoreService {
    suspend fun upsertComment(comment: UserProduct)

}