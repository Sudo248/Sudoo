package com.sudo248.orderservice.repository.entity.order;

public enum OrderStatus {
    PREPARE,
    TAKE_ORDER,
    SHIPPING,
    RECEIVED,
    CANCELED;

    public static OrderStatus fromValue(String value) {
        switch (value.toLowerCase()) {
            case "prepare":
                return PREPARE;
            case "take_order":
                return TAKE_ORDER;
            case "shipping":
                return SHIPPING;
            case "received":
                return RECEIVED;
            default:
                return CANCELED;
        }
    }
}
