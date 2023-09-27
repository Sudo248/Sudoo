package com.sudoo.productservice.service

import com.sudoo.productservice.dto.SupplierDto
import com.sudoo.productservice.dto.SupplierInfoDto
import kotlinx.coroutines.flow.Flow

interface SupplierService {
    suspend fun getSuppliers(): Flow<SupplierDto>
    suspend fun getSupplierById(supplierId: String): SupplierDto
    suspend fun getSupplierInfoById(supplierId: String): SupplierInfoDto
    suspend fun getSupplierByUserId(userId: String): SupplierDto

    suspend fun upsertSupplier(supplierDto: SupplierDto): SupplierDto

    suspend fun deleteSupplier(supplierId: String): String
}