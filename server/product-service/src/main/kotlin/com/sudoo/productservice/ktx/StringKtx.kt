package com.sudoo.productservice.ktx

fun String.toBoolean(): Boolean {
    return this.lowercase() == "true"
}