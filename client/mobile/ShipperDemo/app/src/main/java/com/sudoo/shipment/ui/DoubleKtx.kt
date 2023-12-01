package com.sudoo.shipment.ui

fun Double.format(digit: Int = 1):String {
    return String.format("%.${digit}f", this)
}