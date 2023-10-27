package com.sudoo.apigateway.config

enum class SudooServices(val id: String, val uri: String, val pattern: Array<String>) {
    AUTH_SERVICE("AUTH-SERVICE", "lb://AUTH-SERVICE", arrayOf("/api/v1/auth/**")),
    PRODUCT_SERVICE("PRODUCT-SERVICE", "lb://PRODUCT-SERVICE", arrayOf("/api/v1/discovery/**", "/api/v1/promotions/**")),
    STORAGE_SERVICE("STORAGE-SERVICE", "lb://STORAGE-SERVICE", arrayOf("/api/v1/storage/**")),
    USER_SERVICE("USER-SERVICE", "lb://USER-SERVICE", arrayOf("/api/v1/users/**", "/api/v1/addresses/**")),
    CART_SERVICE("CART-SERVICE", "lb://CART-SERVICE", arrayOf("/api/v1/carts/**")),
    PAYMENT_SERVICE("PAYMENT-SERVICE", "lb://PAYMENT-SERVICE", arrayOf("/api/v1/payment/**", "/payment/**")),
    ORDER_SERVICE("ORDER-SERVICE", "lb://ORDER-SERVICE", arrayOf("/api/v1/orders/**")),
    NOTIFICATION_SERVICE("NOTIFICATION-SERVICE", "lb://NOTIFICATION-SERVICE", arrayOf("/api/v1/notifications/**", "/api/v1/chats/**")),
}