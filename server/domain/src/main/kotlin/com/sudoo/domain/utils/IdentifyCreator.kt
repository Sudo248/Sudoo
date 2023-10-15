package com.sudoo.domain.utils

import java.util.*

object IdentifyCreator {
    fun create(): String {
        val uuid = UUID.randomUUID().toString()
        return "${uuid.substring(14, 18)}${uuid.substring(9, 13)}${uuid.substring(0, 8)}${uuid.substring(19, 23)}${uuid.substring(24)}"
    }

    fun createOrElse(id: String?): String {
        return if (!id.isNullOrEmpty()) id else create()
    }

    fun genProductSku(brand: String, nameProduct: String): String {
        // brand = SUDO, name = duong
        // SUDU12345678
        return "${brand.substring(0, 2).uppercase()}${nameProduct.substring(0, 2).uppercase()}${
            (Random(System.currentTimeMillis()).nextInt(
                    33554432
            ) + 10000000)
        }"
    }

    fun genProductSkuOrElse(sku: String?, brand: String, nameProduct: String): String {
        return if (!sku.isNullOrEmpty()) sku else genProductSku(brand, nameProduct)
    }

    fun genRefreshToken(): String {
        return UUID.randomUUID().toString()
    }
}