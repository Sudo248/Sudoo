package com.sudoo.cartservice.service.impl

import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartSupplierProductDto
import com.sudoo.cartservice.internal.DiscoveryService
import com.sudoo.cartservice.repository.CartRepository
import com.sudoo.cartservice.repository.entity.CartSupplierProduct
import com.sudoo.cartservice.service.CartService
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class CartServiceImpl(
        val cartRepository: CartRepository,
        val cartSupplierProductRepository: CartRepository,
        val discoveryService: DiscoveryService
) : CartService {
    override suspend fun createNewCart(userId: String): CartDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateStatusCart(userId: String): CartDto {
        TODO("Not yet implemented")
    }

    override suspend fun getCartById(userId: String, cartId: String, hasRoute: Boolean): CartDto {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveCartByUserId(userId: String): CartDto {
        TODO("Not yet implemented")
    }

    override suspend fun getCountItemActiveCart(userId: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getSupplierProduct(userId: String, cartId: String, list: List<CartSupplierProduct>, hasRoute: Boolean): List<CartSupplierProductDto> {
        TODO("Not yet implemented")
    }
}