package com.sudoo.cartservice.service

import com.sudoo.cartservice.controller.dto.ProductInfoDto
import com.sudoo.cartservice.controller.dto.order.OrderProductInfoDto

interface ProductService {
    suspend fun getProductInfo(productId: String): ProductInfoDto

    suspend fun getListOrderProductInfoByIds(productIds: List<String>): List<OrderProductInfoDto>
}