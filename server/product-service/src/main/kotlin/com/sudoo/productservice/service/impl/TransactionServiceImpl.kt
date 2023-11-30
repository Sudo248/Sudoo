package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.NotFoundException
import com.sudoo.productservice.dto.TransactionDto
import com.sudoo.productservice.mapper.toTransactionDto
import com.sudoo.productservice.repository.SupplierRepository
import com.sudoo.productservice.repository.TransactionRepository
import com.sudoo.productservice.service.TransactionService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl (
    private val transactionRepository: TransactionRepository,
    val supplierRepository: SupplierRepository
) : TransactionService {
    override suspend fun getAllTransactionByOwnerId(ownerId: String): List<TransactionDto> {
        return transactionRepository.findAllByOwnerId(ownerId).map { it.toTransactionDto() }.toList()
    }

    override suspend fun getAllTransactionBySelf(userId: String): List<TransactionDto> {
        val supplier = supplierRepository.findByUserId(userId) ?: throw NotFoundException("Not found supplier for user $userId")
        return getAllTransactionByOwnerId(supplier.supplierId)
    }
}