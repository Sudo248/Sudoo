package com.sudo248.sudoo.ui.ktx

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.sudo248.sudoo.BuildConfig
import java.io.File

fun Context.createTempPictureUri(
    provider: String = "${BuildConfig.APPLICATION_ID}.provider",
    fileName: String = "picture_${System.currentTimeMillis()}",
    fileExtension: String = ".png"
): Uri {
    val tempFile = File.createTempFile(
        fileName, fileExtension, cacheDir
    ).apply {
        createNewFile()
    }

    return FileProvider.getUriForFile(applicationContext, provider, tempFile)
}

fun Context.getUriForFile(file: File): Uri {
    return FileProvider.getUriForFile(
        applicationContext,
        "${BuildConfig.APPLICATION_ID}.provider",
        file
    )
}

fun Context.createTempFile(prefix: String, suffix: String): File {
    return File.createTempFile(prefix, suffix, cacheDir)
}