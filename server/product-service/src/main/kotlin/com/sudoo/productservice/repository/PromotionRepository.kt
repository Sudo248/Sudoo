package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Promotion
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PromotionRepository : CoroutineCrudRepository<Promotion, String> {

    suspend fun deleteAllBySupplierId(supplierId: String)

}