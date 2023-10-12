package com.sudo248.sudoo.ui.activity.payment.fragment

interface ViewController {
    fun openVnPaySdk()
    fun payWithCODSuccess()
    fun toast(message: String)
}