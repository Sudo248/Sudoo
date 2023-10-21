package com.sudoo.productservice.service

import com.sudoo.productservice.dto.SupplierDto
import com.sudoo.productservice.dto.SupplierInfoDto
import com.sudoo.productservice.dto.UpsertSupplierDto

interface SupplierService {
    suspend fun getSuppliers(): List<SupplierDto>
    suspend fun getSupplierById(supplierId: String): SupplierDto
    suspend fun getSupplierInfoById(supplierId: String): SupplierInfoDto
    suspend fun getSupplierByUserId(userId: String): SupplierDto
    suspend fun getSupplierInfoByUserId(userId: String): SupplierInfoDto

    suspend fun upsertSupplier(userId: String, supplierDto: UpsertSupplierDto): SupplierDto

    suspend fun deleteSupplier(supplierId: String): String
}