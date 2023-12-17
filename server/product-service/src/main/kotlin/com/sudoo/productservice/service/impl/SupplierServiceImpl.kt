package com.sudoo.productservice.service.impl

import com.sudoo.domain.exception.BadRequestException
import com.sudoo.domain.exception.NotFoundException
import com.sudoo.domain.validator.ProductValidator
import com.sudoo.productservice.dto.SupplierDto
import com.sudoo.productservice.dto.SupplierInfoDto
import com.sudoo.productservice.mapper.toSupplier
import com.sudoo.productservice.mapper.toSupplierDto
import com.sudoo.productservice.mapper.toSupplierInfoDto
import com.sudoo.productservice.repository.ProductRepository
import com.sudoo.productservice.repository.SupplierRepository
import com.sudoo.productservice.service.SupplierService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val productRepository: ProductRepository,
) : SupplierService {
    override suspend fun getSuppliers(): List<SupplierDto> {
        return supplierRepository.findAll()
            .map {
                it.toSupplierDto(
                    totalProducts = productRepository.countBySupplierId(it.supplierId).toInt()
                )
            }
            .toList()
    }

    override suspend fun getSupplierById(supplierId: String): SupplierDto {
        val supplier = supplierRepository.findById(supplierId) ?: throw NotFoundException("Not found supplier $supplierId")
        val totalProducts = productRepository.countBySupplierId(supplierId).toInt()
        return supplier.toSupplierDto(totalProducts)
    }

    override suspend fun getSupplierInfoById(supplierId: String): SupplierInfoDto {
        val supplier = supplierRepository.findById(supplierId) ?: throw NotFoundException("Not found supplier $supplierId")
        return supplier.toSupplierInfoDto()
    }

    override suspend fun getSupplierByUserId(userId: String): SupplierDto {
        val supplier = supplierRepository.getByUserId(userId) ?: throw NotFoundException("Not found supplier of user $userId")
        val totalProducts = productRepository.countBySupplierId(supplier.supplierId).toInt()
        return supplier.toSupplierDto(totalProducts)
    }

    override suspend fun getSupplierInfoByUserId(userId: String): SupplierInfoDto {
        val supplier = supplierRepository.getByUserId(userId) ?: throw NotFoundException("Not found supplier of user $userId")
        return supplier.toSupplierInfoDto()
    }

    override suspend fun upsertSupplier(userId: String, supplierDto: SupplierDto): SupplierDto {
        if (!ProductValidator.validateBrand(supplierDto.brand)) throw BadRequestException("Brand is too short, require at least 3 characters")
        val supplier = supplierDto.toSupplier(userId)
        supplierRepository.save(supplier)
        return supplier.toSupplierDto(totalProducts = 0)
    }

    override suspend fun deleteSupplier(supplierId: String): String {
        val supplier =
            supplierRepository.findById(supplierId) ?: throw NotFoundException("Not found supplier $supplierId")
        supplierRepository.delete(supplier)
        return supplierId
    }
}