package com.sudoo.productservice.mapper

import com.sudoo.domain.utils.IdentifyCreator
import com.sudoo.productservice.dto.*
import com.sudoo.productservice.model.Product
import com.sudoo.productservice.model.ProductInfo

fun UpsertProductDto.toProduct(supplierId: String, brand: String): Product {
    return Product(
        productId = IdentifyCreator.createOrElse(productId),
        supplierId = supplierId,
        sku = IdentifyCreator.genProductSkuOrElse(sku, brand = brand, nameProduct = name!!),
        name = name,
        description = description!!,
        price = price ?: listedPrice!!,
        listedPrice = listedPrice!!,
        amount = amount!!,
        soldAmount = soldAmount ?: 0,
        rate = 5.0f,
        totalRateAmount = 0,
        discount = discount ?: 0,
        startDateDiscount = startDateDiscount,
        endDateDiscount = endDateDiscount,
        sellable = sellable ?: true,
    )
}

fun UpsertProductDto.combineProduct(product: Product): Product {
    return Product(
        productId = productId ?: product.productId,
        supplierId = product.supplierId,
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

fun Product.toProductDto(
    supplier: SupplierInfoDto? = null,
    categories: List<CategoryInfoDto>? = null,
    images: List<ImageDto>? = null,
): ProductDto {
    return ProductDto(
        productId = productId,
        sku = sku,
        name = name,
        description = description,
        price = price,
        listedPrice = listedPrice,
        amount = amount,
        soldAmount = soldAmount,
        rate = rate,
        discount = discount,
        startDateDiscount = startDateDiscount,
        endDateDiscount = endDateDiscount,
        sellable = sellable,
        supplier = supplier,
        categories = categories,
        images = images,
    )
}

fun ProductInfo.toProductInfoDto(): ProductInfoDto {
    return ProductInfoDto(
        productId = productId,
        sku = sku,
        name = name,
        price = price,
        listedPrice = listedPrice,
        discount = discount,
        startDateDiscount = startDateDiscount,
        endDateDiscount = endDateDiscount,
        brand = brand,
        rate = rate,
        sellable = sellable,
        images = images.orEmpty(),
    )
}