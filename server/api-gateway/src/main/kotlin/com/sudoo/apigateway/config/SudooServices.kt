package com.sudoo.apigateway.config

enum class SudooServices(val id: String, val uri: String, val pattern: Array<String>) {
    AUTH_SERVICE("AUTH-SERVICE", "lb://AUTH-SERVICE", arrayOf("/api/v1/auth/**")),
    PRODUCT_SERVICE("PRODUCT-SERVICE", "lb://PRODUCT-SERVICE", arrayOf("/api/v1/discovery/**", "/api/v1/promotion/**")),
    IMAGE_SERVICE("IMAGE-SERVICE", "lb://IMAGE-SERVICE", arrayOf("/api/v1/images/**")),
    USER_SERVICE("USER-SERVICE", "lb://USER-SERVICE", arrayOf("/api/v1/user/**")),
    CART_SERVICE("CART-SERVICE", "lb://CART-SERVICE", arrayOf("/api/v1/cart/**")),
    PAYMENT_SERVICE("PAYMENT-SERVICE", "lb://PAYMENT-SERVICE", arrayOf("/api/v1/payment/**", "/payment/**")),
    ORDER_SERVICE("ORDER-SERVICE", "lb://ORDER-SERVICE", arrayOf("/api/v1/order/**")),
    NOTIFICATION_SERVICE("NOTIFICATION-SERVICE", "lb://NOTIFICATION-SERVICE", arrayOf("/api/v1/notification/**", "/api/v1/chat/**")),
}