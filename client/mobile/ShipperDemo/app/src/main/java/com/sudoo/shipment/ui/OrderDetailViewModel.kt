package com.sudoo.shipment.ui

import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.navigation.IntentDirections
import com.sudoo.shipment.api.OrderService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class OrderDetailViewModel @Inject constructor(
    private val orderService: OrderService
): BaseViewModel<IntentDirections>() {

    fun getOrderDetail(orderId: String) = launch {

    }
}