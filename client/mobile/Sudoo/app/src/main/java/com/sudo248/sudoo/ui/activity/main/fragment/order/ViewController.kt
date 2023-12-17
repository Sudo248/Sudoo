package com.sudo248.sudoo.ui.activity.main.fragment.order

interface ViewController {
    fun openVnPaySdk()
    fun payWithCODSuccess()
    fun toast(message: String)
}