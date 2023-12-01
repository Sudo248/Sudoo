package com.sudo248.sudoo.domain.entity.payment

enum class PaymentType(val value: String) {
    VN_PAY("VnPay"),
    COD("Trả khi giao hàng.");

    companion object {
        fun fromString(value: String): PaymentType {
            return when(value) {
                "VnPay" -> VN_PAY
                else -> COD
            }
        }
    }
}