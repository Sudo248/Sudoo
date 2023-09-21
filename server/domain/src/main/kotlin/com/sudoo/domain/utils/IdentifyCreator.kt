package com.sudoo.domain.utils

import java.util.*

object IdentifyCreator {
    fun create(key: String? = null): String {
        return "${if (key != null) UUID.fromString(key) else UUID.randomUUID()}}"
    }

    fun createOrElse(id: String?): String {
        return if (!id.isNullOrEmpty()) id else create()
    }

    fun genProductSku(brand: String, nameProduct: String): String {
        return "${brand.substring(0, 2).uppercase()}${nameProduct.substring(0, 2).uppercase()}${
            (Random(System.currentTimeMillis()).nextInt(
                80000
            ) + 10000)
        }"
    }

    fun genProductSkuOrElse(sku: String?, brand: String, nameProduct: String): String {
        return if (!sku.isNullOrEmpty()) sku else genProductSku(brand, nameProduct)
    }
}