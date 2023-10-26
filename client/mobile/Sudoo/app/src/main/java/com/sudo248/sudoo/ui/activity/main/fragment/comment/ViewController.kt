package com.sudo248.sudoo.ui.activity.main.fragment.comment

import android.net.Uri
import com.sudo248.sudoo.domain.entity.discovery.UpsertComment

interface ViewController {
    fun toast(message: String)
    fun getPathImageFromUri(uri: Uri): String
    fun getUpsertComment(): UpsertComment
}