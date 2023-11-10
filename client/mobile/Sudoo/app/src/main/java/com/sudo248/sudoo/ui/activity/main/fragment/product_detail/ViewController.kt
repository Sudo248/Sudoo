package com.sudo248.sudoo.ui.activity.main.fragment.product_detail

import android.widget.Toast
import androidx.annotation.StringRes

interface ViewController {
    fun setBadgeCart(amount: Int)
    fun openContact(url: String)

    fun toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT)
}