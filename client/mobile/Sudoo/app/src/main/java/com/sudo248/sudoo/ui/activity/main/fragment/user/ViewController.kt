package com.sudo248.sudoo.ui.activity.main.fragment.user

import android.net.Uri
import java.util.*

interface ViewController {
    fun showDialogDatePicker(now: Date? = null)
    fun showBottomSheetChooseAddress()
    fun toast(message: String)
    fun getPathImageFromUri(uri: Uri): String
    fun pickImage()
}