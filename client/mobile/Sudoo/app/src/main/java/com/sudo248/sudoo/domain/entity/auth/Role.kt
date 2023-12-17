package com.sudo248.sudoo.domain.entity.auth

enum class Role(val value: String) {
    CONSUMER("Thành viên"),
    STAFF("Nhân viên");

    companion object {
        fun fromStringValue(value: String): Role {
            if (value == "Nhân viên") return STAFF
            return CONSUMER
        }
    }
}