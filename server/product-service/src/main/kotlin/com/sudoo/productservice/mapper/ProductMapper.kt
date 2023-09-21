package com.sudoo.productservice.mapper

import com.sudoo.productservice.controller.dto.UpsertProductDto
import com.sudoo.productservice.model.Product

fun UpsertProductDto.toProduct(): Product {
    return Product(
        productId = productId,
        supplierId = supplierId!!,
        sku = sku!!,
        name = name!!,
        description = description!!,
        price = price!!,
        listedPrice = listedPrice!!,
        amount = amount!!,
        soldAmount = soldAmount!!,
        rate = 5.0f,
        totalRateAmount = 0,
        discount = discount!!,
        startDateDiscount = startDateDiscount!!,
        endDateDiscount = endDateDiscount!!,
        sellable = sellable!!,
    )
}

fun UpsertProductDto.combineProduct(product: Product): Product {
    return Product(
        productId = productId ?: product.productId,
        supplierId = supplierId ?: product.supplierId,
        sku = sku ?: product.sku,
        name = name ?: product.name,
        description = description ?: product.description,
        price = price ?: product.price,
        listedPrice = listedPrice ?: product.listedPrice,
        amount = amount ?: product.amount,
        soldAmount = soldAmount ?: product.amount,
        rate = product.rate,
        totalRateAmount = product.totalRateAmount,
        discount = discount ?: product.discount,
        startDateDiscount = startDateDiscount ?: product.startDateDiscount,
        endDateDiscount = endDateDiscount ?: product.endDateDiscount,
        sellable = sellable ?: product.sellable,
    )
}