package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Supplier
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : CoroutineCrudRepository<Supplier, String> {
     suspend fun findByName(name: String): Flow<Supplier>
}