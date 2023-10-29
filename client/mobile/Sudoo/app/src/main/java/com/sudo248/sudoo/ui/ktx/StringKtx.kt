package com.sudo248.sudoo.ui.ktx

import com.denzcoskun.imageslider.models.SlideModel
import com.sudo248.sudoo.BuildConfig

fun String.toImageUrl(): String {
    if (!startsWith("http")) return "${BuildConfig.BASE_URL}storage/images/$this"
    return this
}

fun String.toSlideModel(): SlideModel {
    return SlideModel(imageUrl = this.toImageUrl())
}