package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.AddSupplierProductDto
import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartSupplierProductDto
import com.sudoo.cartservice.internal.DiscoveryService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.CartSupplierProductRepository
import com.sudoo.cartservice.repository.entity.CartSupplierProduct
import com.sudoo.cartservice.service.CartService
import com.sudoo.cartservice.service.CartSupplierProductService
import org.springframework.stereotype.Service

@Service
class CartSupplierProductServiceImpl(
        val cartService: CartService,
        val cartRepository: CartRepository,
        val cartSupplierProductRepository: CartSupplierProductRepository,
        val discoveryService: DiscoveryService
) : CartSupplierProductService {
    override suspend fun addSupplierProductToCart(userId: String, addSupplierProductDto: CartSupplierProductDto): CartDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateSupplierProductToCart(userId: String, cartId: String, addSupplierProductDtoList: List<AddSupplierProductDto>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSupplierProduct(userId: String?, cartId: String?, supplierId: String?, productId: String?): CartDto? {
        TODO("Not yet implemented")
    }
}