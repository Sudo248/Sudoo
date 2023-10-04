package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.AddSupplierProductDto
import com.sudoo.cartservice.controller.dto.CartDto
import com.sudoo.cartservice.controller.dto.CartSupplierProductDto

interface CartSupplierProductService {
    suspend fun addSupplierProductToCart(
            userId: String,
            addSupplierProductDto: CartSupplierProductDto
    ): CartDto

    suspend fun updateSupplierProductToCart(
            userId: String,
            cartId: String,
            addSupplierProductDtoList: List<AddSupplierProductDto>)

    suspend fun deleteSupplierProduct(
            userId: String?,
            cartId: String?,
            supplierId: String?,
            productId: String?
    ): CartDto?
}