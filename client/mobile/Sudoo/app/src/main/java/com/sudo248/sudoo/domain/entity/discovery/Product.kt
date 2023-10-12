package com.sudo248.sudoo.domain.entity.discovery


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 23:09 - 12/03/2023
 */
data class Product(
    val productId: String,
    val name: String,
    val description: String,
    val sku: String,
    val images: List<String>,
    val supplierProducts: List<SupplierProduct>,
    val isLike: Boolean = false,
) :  java.io.Serializable
