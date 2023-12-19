package com.sudoo.productservice.service.impl

import com.sudoo.domain.base.OffsetRequest
import com.sudoo.domain.exception.ApiException
import com.sudoo.domain.utils.Logger
import com.sudoo.productservice.dto.RecommendListProductDto
import com.sudoo.productservice.dto.RecommendProductDto
import com.sudoo.productservice.dto.RecommendUserProductDto
import com.sudoo.productservice.repository.CategoryProductRepository
import com.sudoo.productservice.service.RecommendService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodyOrNull

@Service
class RecommendServiceImpl(
    @Qualifier("recommend-service") private val client: WebClient,
    private val categoryProductRepository: CategoryProductRepository
) : RecommendService {
    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val scope = CoroutineScope(
            newSingleThreadContext("Recommend product thread") + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                Logger.error(throwable)
            },
        )
    }

    override suspend fun getListRecommendProduct(userId: String, offsetRequest: OffsetRequest): RecommendListProductDto {
        return client.get()
            .uri("/predictions/${userId}?offset=${offsetRequest.offset}&limit=${offsetRequest.limit}")
            .retrieve()
            .awaitBodyOrNull<RecommendListProductDto>() ?: throw ApiException(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Something went wrong at recommend service"
        )
    }

    override suspend fun upsertProduct(productId: String) {
        scope.launch {
            val recommendProduct = RecommendProductDto(
                productId = productId,
                categories = categoryProductRepository.getByProductId(productId).map { it.categoryId }.toList()
            )
            client.post()
                .uri("/products")
                .bodyValue(recommendProduct)
                .retrieve()
                .awaitBodyOrNull<Any>()
        }
    }

    override suspend fun upsertUserProduct(userProduct: RecommendUserProductDto) {
        scope.launch {
            client.post()
                .uri("/user-product")
                .bodyValue(userProduct)
                .retrieve()
                .awaitBodyOrNull<Any>()
        }
    }
}