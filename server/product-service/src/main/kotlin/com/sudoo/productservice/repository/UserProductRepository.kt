package com.sudoo.productservice.repository

import com.sudoo.productservice.model.UserProduct
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserProductRepository : CoroutineCrudRepository<UserProduct, String> {

    @Query("""
        SELECT * FROM users_products
        WHERE users_products.product_id = :productId
        LIMIT :_limit
        OFFSET :_offset
    """)
    fun getAllByProductIdWithOffset(
        @Param("productId") productId: String,
        @Param("_offset") offset: Int = 0,
        @Param("_limit") limit: Int = 0
    ): Flow<UserProduct>

    suspend fun countByProductId(productId: String): Long
}