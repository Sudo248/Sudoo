package com.sudo248.sudoo.data.dto.discovery

import java.time.LocalDateTime

data class ProductDto(
    val productId: String,
    val supplierId:String,
    val name: String,
    val description: String,
    val brand:String,
    val sku: String,
    val price: Double,
    val listedPrice: Double,
    val amount: Int,
    val soldAmount: Int,
    val rate: Float,
    val discount: Int,
    val startDateDiscount: LocalDateTime,
    val endDateDiscount: LocalDateTime,
    val saleable: Boolean,
    val weight: Int,
    val height: Int,
    val length: Int,
    val width: Int,
    val extras: ProductExtrasDto,
    val images: List<ImageDto> = listOf(),
    val supplier: SupplierInfoDto? = null,
    val categories: List<CategoryInfoDto>? = null,
)