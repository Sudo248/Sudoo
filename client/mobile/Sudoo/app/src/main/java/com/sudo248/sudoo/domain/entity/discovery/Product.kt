package com.sudo248.sudoo.domain.entity.discovery

import java.time.LocalDateTime


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 23:09 - 12/03/2023
 */
data class Product(
    val productId: String,
    val supplierId: String ,
    val name: String,
    val description: String,
    val brand: String ,
    val sku: String,
    val images: List<String>,
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
    val extras: ProductExtras,
    val supplier: SupplierInfo? = null,
    val categories: List<CategoryInfo>? = null,
) : java.io.Serializable
