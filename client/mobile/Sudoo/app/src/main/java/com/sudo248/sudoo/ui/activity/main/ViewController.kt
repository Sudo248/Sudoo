package com.sudo248.sudoo.ui.activity.main;

import android.net.Uri

interface ViewController {
    fun pickImage()
    fun takeImage(uri: Uri)
    fun createTempPictureUri(): Uri
    fun requestPermission(permission: String, callback: (Boolean) -> Unit)
}
