package com.sudo248.sudoo.ui.models.order

import com.sudo248.sudoo.domain.entity.order.OrderStatus

enum class OrderStatusTab(val statusValue: String) {
    PREPARE(OrderStatus.PREPARE.value),
    TAKE_ORDER(OrderStatus.READY.value),
    SHIPPING("${OrderStatus.TAKE_ORDER.value},${OrderStatus.SHIPPING.value},${OrderStatus.DELIVERED.value}"),
    RECEIVED(OrderStatus.RECEIVED.value)
}