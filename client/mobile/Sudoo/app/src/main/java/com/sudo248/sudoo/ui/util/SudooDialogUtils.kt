package com.sudo248.sudoo.ui.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.ColorRes
import com.sudo248.base_android.R
import com.sudo248.base_android.ktx.showWithTransparentBackground

object SudooDialogUtils {
    fun showConfirmDialog(
        context: Context,
        title: String,
        description: String? = null,
        positive: String? = null,
        @ColorRes textPositiveColor: Int? = null,
        negative: String? = null,
        @ColorRes textNegativeColor: Int? = null,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null,
    ) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null, false)
        dialog.setContentView(view)
        view.run {
            findViewById<TextView>(R.id.txtTitle).apply {
                text = title
            }

            description?.let {
                findViewById<TextView>(R.id.txtDescription).apply {
                    text = it
                }
            }

            findViewById<TextView>(R.id.txtPositive).apply {
                text = positive ?: context.getString(R.string.sudo_ok)
                textPositiveColor?.let { color ->
                    setTextColor(color)
                }
                setOnClickListener {
                    dialog.dismiss()
                    onPositive?.invoke()
                }
            }
            findViewById<TextView>(R.id.txtNegative).apply {
                text = negative ?: context.getString(R.string.sudo_cancel)
                textNegativeColor?.let { color ->
                    setTextColor(color)
                }
                setOnClickListener {
                    dialog.dismiss()
                    onNegative?.invoke()
                }
            }
        }

        dialog.showWithTransparentBackground()
    }
}