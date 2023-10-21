package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.ApiException
import com.sudoo.productservice.dto.ghn.CreateStoreRequest
import com.sudoo.productservice.dto.ghn.GHNResponse
import com.sudoo.productservice.dto.ghn.GHNStoreDto
import com.sudoo.productservice.service.GHNService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class GHNServiceImpl (
    @Qualifier("ghn-service") private val client: WebClient
) : GHNService {
    override suspend fun createStore(request: CreateStoreRequest): GHNStoreDto {
        val response = client.post()
            .bodyValue(request)
            .retrieve()
            .awaitBodyOrNull<GHNResponse<GHNStoreDto>>() ?: throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Create GHN store error")
        return response.data ?: throw ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Create GHN store error")
    }
}