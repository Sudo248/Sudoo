package com.sudoo.productservice.dto

data class TransactionDto(
    val transactionId: String?,
    val ownerId: String,
    var amount: Double,
    val description: String,
)