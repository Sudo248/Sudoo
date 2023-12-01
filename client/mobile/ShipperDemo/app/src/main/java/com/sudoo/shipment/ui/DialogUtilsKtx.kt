package com.sudoo.shipment.ui

import android.app.Dialog
import android.content.Context
import com.sudo248.base_android.utils.DialogUtils
import com.sudoo.shipment.R

fun DialogUtils.showErrorDialog(context: Context, message: String): Dialog {
    return showDialog(
        context = context,
        title = context.getString(R.string.error),
        textColorTitle = R.color.red,
        description = message,
        backgroundConfirmColor = R.color.red
    )
}