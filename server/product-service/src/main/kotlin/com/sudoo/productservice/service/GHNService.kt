package com.sudoo.productservice.service

import com.sudoo.productservice.dto.ghn.CreateStoreRequest
import com.sudoo.productservice.dto.ghn.GHNResponse
import com.sudoo.productservice.dto.ghn.GHNStoreDto

interface GHNService {
    suspend fun createStore(request: CreateStoreRequest): GHNStoreDto
}