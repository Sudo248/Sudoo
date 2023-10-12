package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.discovery.*
import com.sudo248.sudoo.domain.entity.discovery.*
import kotlin.random.Random

fun SupplierProductDto.toSupplierProduct(): SupplierProduct {
    return SupplierProduct(
        supplierId = supplierId,
        productId = productId,
        route = route,
        amountLeft = amountLeft,
        price = price,
        soldAmount = soldAmount,
        rate = if (rate <= 0) Random.nextDouble(3.0,5.0) else rate,
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        productId = productId,
        name = name,
        description = description,
        sku = sku,
        images = images?.map { it.url } ?: listOf("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YnVyZ2VyfGVufDB8fDB8fA%3D%3D&w=1000&q=80"),
        supplierProducts = supplierProducts?.map { it.toSupplierProduct() } ?: listOf()
    )
    //
}

fun CategoryDto.toCategory(): Category {
    return Category(
        categoryId = categoryId,
        name = name,
        imageUrl = image,
        products = products.map { it.toProduct() }
    )
}

fun CategoryInfoDto.toCategoryInfo(): CategoryInfo {
    return CategoryInfo(
        categoryId, name, image, supplierId
    )
}

fun SupplierDto.toSupplier(): Supplier {
    return Supplier(
        supplierId, name, avatar
    )
}