package com.sudoo.shipment.ui

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sudoo.shipment.BuildConfig
import com.sudoo.shipment.R

fun loadImage(image: ImageView, url: String) {
    if (url.isEmpty()) return
    var imageUrl = url
    if (!imageUrl.startsWith("http")) imageUrl = "${BuildConfig.BASE_URL}storage/images/$url"
    val circularProgressDrawable = CircularProgressDrawable(image.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide
        .with(image.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_error)
        .into(image)
}