package com.sudoo.productservice.dto

data class TransactionDto(
    val transactionId: String?,
    var ownerId: String,
    var amount: Double,
    val description: String,
)