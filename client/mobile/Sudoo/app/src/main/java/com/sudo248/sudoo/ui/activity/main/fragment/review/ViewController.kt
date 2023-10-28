package com.sudo248.sudoo.ui.activity.main.fragment.review

import android.net.Uri
import com.sudo248.sudoo.domain.entity.discovery.UpsertReview

interface ViewController {
    fun toast(message: String)
    fun getPathImageFromUri(uri: Uri): String
    fun getUpsertReview(): UpsertReview
}