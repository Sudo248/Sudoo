package com.sudo248.sudoo.ui.ktx

import android.app.Dialog
import android.content.Context
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 23:44 - 11/03/2023
 */

fun DialogUtils.showErrorDialog(context: Context, message: String): Dialog {
    return showDialog(
        context = context,
        title = context.getString(R.string.error),
        textColorTitle = R.color.red,
        description = message,
        backgroundConfirmColor = R.color.red
    )
}