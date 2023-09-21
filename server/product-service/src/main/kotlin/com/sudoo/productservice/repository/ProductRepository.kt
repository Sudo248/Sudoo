package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Product
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface ProductRepository : CoroutineCrudRepository<Product, String> {
    suspend fun getProductsBySupplierId(supplierId: String): Flow<Product>
    suspend fun getProductBySku(sku: String): Product
    suspend fun findByName(name: String): Flow<Product>

    @Transactional
    @Query("""
        INSERT INTO categories_products VALUES (:productId, :categoryId);
    """)
    suspend fun addProductToCategory(@Param("productId") productId: String, @Param("categoryId")  categoryId: String): Boolean
}