package com.sudoo.productservice.ktx

fun Int?.orZero(): Int = this ?: 0

fun Float?.orZero(): Float = this ?: 0.0f