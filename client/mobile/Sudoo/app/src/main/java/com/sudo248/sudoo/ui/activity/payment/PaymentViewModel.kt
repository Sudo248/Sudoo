package com.sudo248.sudoo.ui.activity.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.sudoo.domain.entity.invoice.Invoice
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.payment.PaymentStatus
import com.sudo248.sudoo.domain.entity.payment.PaymentType
import com.sudo248.sudoo.domain.repository.InvoiceRepository
import com.sudo248.sudoo.domain.repository.PaymentRepository
import com.sudo248.sudoo.ui.activity.payment.fragment.ViewController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class PaymentViewModel : BaseViewModel<IntentDirections>() {
    
}