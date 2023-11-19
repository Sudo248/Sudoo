package com.sudo248.orderservice.repository.entity.order;

public enum OrderStatus {
    // người bán hàng chuẩn bị đơn hàng.
    PREPARE,
    // Người bán hàng sẵn sàng giao cho shipper.
    READY,
    // Shipper đã nhận hàng.
    TAKE_ORDER,
    // Đang vận chuyển đơn hàng.
    SHIPPING,
    // User đã nhận được hàng
    RECEIVED,
    // Đơn bị huỷ
    CANCELED;

    public static OrderStatus fromValue(String value) {
        if (value == null) return null;
        switch (value.toLowerCase()) {
            case "prepare":
                return PREPARE;
            case "ready":
                return READY;
            case "take_order":
                return TAKE_ORDER;
            case "shipping":
                return SHIPPING;
            case "received":
                return RECEIVED;
            case "canceled":
                return CANCELED;
            default:
                return null;
        }
    }
}
