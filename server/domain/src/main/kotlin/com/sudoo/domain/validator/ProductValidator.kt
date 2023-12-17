package com.sudoo.domain.validator

object ProductValidator {
    private const val SKU_PATTERN = "[A-Z]{4}[0-9]{8}"

    fun validateSku(sku: String): Boolean = sku.matches(SKU_PATTERN.toRegex())
    fun validateBrand(brand: String): Boolean = brand.length >= 3
    fun validateNameProduct(name: String): Boolean = name.length >= 5
    fun validateAmount(amount: Int?): Boolean = amount != null && amount > 0
    fun validatePrice(price: Float?, listedPrice: Float?) =
        (price != null && price > 0) || (listedPrice != null && listedPrice > 0)
}