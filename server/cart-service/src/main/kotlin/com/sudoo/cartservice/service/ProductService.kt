package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.ProductInfoDto

interface ProductService {
    suspend fun getProductInfo(productId: String): ProductInfoDto
}