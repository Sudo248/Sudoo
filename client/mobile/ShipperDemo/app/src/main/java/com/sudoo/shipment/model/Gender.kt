package com.sudoo.shipment.model

enum class Gender(val value: String) {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    companion object {
        fun fromStringValue(value: String): Gender {
            return when(value){
                "Nam" -> MALE
                "Nữ" -> FEMALE
                else -> OTHER
            }
        }
    }
}