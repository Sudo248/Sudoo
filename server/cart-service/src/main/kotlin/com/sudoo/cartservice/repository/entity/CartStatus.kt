package com.sudoo.cartservice.repository.entity

enum class CartStatus(val value: String) {
    ACTIVE("active"),
    PROCESSING("processing"),
    COMPLETED("completed"),
}