package com.sudo248.sudoo.ui.ktx

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.requestFocusAndKeyBoard() {
    requestFocus()
    val imm = context.getSystemService(InputMethodManager::class.java)
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}