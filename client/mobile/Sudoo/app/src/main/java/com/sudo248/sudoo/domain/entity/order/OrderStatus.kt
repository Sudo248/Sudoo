package com.sudo248.sudoo.domain.entity.order

import androidx.annotation.StringRes
import com.sudo248.sudoo.R

enum class OrderStatus(val value: String, @StringRes val displayValue: Int) {
    PREPARE("prepare", R.string.prepare),
    READY("ready", R.string.ready),
    TAKE_ORDER("take_order", R.string.take_order),
    SHIPPING("shipping", R.string.shipping),
    DELIVERED("delivered", R.string.delivered),
    RECEIVED("received", R.string.received),
    CANCELED("canceled", R.string.canceled);
}