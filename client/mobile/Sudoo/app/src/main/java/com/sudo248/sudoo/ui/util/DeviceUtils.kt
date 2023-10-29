package com.sudo248.sudoo.ui.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.widget.Toast
import com.sudo248.sudoo.R
import java.util.Objects

object DeviceUtils {

    private const val MIN_OPENGL_VERSION = 3.0

    fun isDeviceSupportAR(activity: Activity): Boolean {
        val openGlVersionString =
            (Objects.requireNonNull(
                activity
                    .getSystemService(Context.ACTIVITY_SERVICE)
            ) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Toast.makeText(
                activity,
                activity.getString(R.string.require_device, "$MIN_OPENGL_VERSION"),
                Toast.LENGTH_LONG
            ).show()
            activity.finish()
            return false
        }
        return true
    }
}