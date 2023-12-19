package com.sudoo.productservice.service

import com.sudoo.productservice.dto.TransactionDto

interface TransactionService {
    suspend fun getAllTransactionByOwnerId(ownerId: String): List<TransactionDto>
    suspend fun getAllTransactionBySelf(userId: String): List<TransactionDto>
}