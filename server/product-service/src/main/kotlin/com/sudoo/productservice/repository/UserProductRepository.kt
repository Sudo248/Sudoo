package com.sudoo.productservice.repository

import com.sudoo.productservice.model.UserProduct
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserProductRepository : CoroutineCrudRepository<UserProduct, String> {
    suspend fun getAllByProductId(productId: String): Flow<UserProduct>
}