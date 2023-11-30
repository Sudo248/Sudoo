package com.sudoo.productservice.repository

import com.sudoo.productservice.model.Transaction
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CoroutineCrudRepository<Transaction, String> {
    fun findAllByOwnerId(ownerId: String): Flow<Transaction>

    fun findAllByOwnerIdAndAmountAfter(ownerId: String, amount: Double): Flow<Transaction>

    fun findAllByOwnerIdAndAmountBefore(ownerId: String, amount: Double): Flow<Transaction>
}