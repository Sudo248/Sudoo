package com.sudo248.sudoo.ui.activity.payment.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.DataState
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.entity.invoice.Invoice
import com.sudo248.sudoo.domain.entity.payment.Payment
import com.sudo248.sudoo.domain.entity.payment.PaymentStatus
import com.sudo248.sudoo.domain.entity.payment.PaymentType
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.repository.InvoiceRepository
import com.sudo248.sudoo.domain.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val invoiceRepository: InvoiceRepository
) : BaseViewModel<NavDirections>() {
    var viewController: ViewController? = null

    private val _userInfo = MutableLiveData<String>()
    val userInfo: LiveData<String> = _userInfo

    private val _currentPaymentType = MutableLiveData<PaymentType>()
    val currentPaymentType: LiveData<PaymentType> = _currentPaymentType

    private val _invoice = MutableLiveData<Invoice>()
    val invoice: LiveData<Invoice> = _invoice

    private val _promotion = MutableLiveData<Promotion?>()
    val promotion: LiveData<Promotion?> = _promotion

    private val _finalPrice = MutableLiveData<Double>()
    val finalPrice: LiveData<Double> = _finalPrice

    var error: SingleEvent<String?> = SingleEvent(null)


    fun onSelectPaymentType(type: PaymentType) {
        _currentPaymentType.postValue(type)
    }

    fun onBack() {
        navigator.back()
    }

    fun onPayment() {
        when (_currentPaymentType.value) {
            null -> {
                viewController?.toast("Hãy chọn phương thức thanh toán")
            }
            PaymentType.VN_PAY -> {
                viewController?.openVnPaySdk()
            }
            else -> {
                payWithCOD()
            }
        }
    }

    fun getInvoice(invoiceId: String) = launch {
        emitState(UiState.LOADING)
        invoiceRepository.getInvoiceById(invoiceId).onSuccess {
                _userInfo.postValue("${it.user.fullName} | ${it.user.phone}\n${it.user.address.fullAddress}")
                _invoice.postValue(it)
                _promotion.postValue(it.promotion)
                _finalPrice.postValue(it.finalPrice)
            }.onError {
                it.printStackTrace()
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun applyPromotion(promotion: Promotion) = launch {
        invoice.value?.let {
            emitState(UiState.LOADING)
            invoiceRepository.updatePromotion(it.invoiceId, promotion.promotionId)
                .onSuccess { newInvoice ->
                    _promotion.postValue(promotion)
                    _finalPrice.postValue(newInvoice.finalPrice)
                }.onError { ex ->
                    error = SingleEvent(ex.message)
                }.bindUiState(_uiState)
        }
    }

    suspend fun getVnPayPaymentUrl(): Deferred<String?> = async {
        emitState(UiState.LOADING)
        val response = paymentRepository.getPaymentUrl(
            Payment(
                paymentType = PaymentType.VN_PAY,
                orderId = _invoice.value!!.invoiceId,
                paymentStatus = PaymentStatus.INIT,
                amount = _invoice.value!!.finalPrice
            )
        )
        when (response) {
            is DataState.Success -> {
                emitState(UiState.SUCCESS)
                response.requireData().paymentUrl
            }
            else -> {
                error = SingleEvent(response.error().message)
                emitState(UiState.ERROR)
                null
            }
        }
    }

    private fun payWithCOD() = launch {
        setState(UiState.LOADING)
        paymentRepository.getPaymentUrl(
            Payment(
                paymentType = PaymentType.CASH,
                orderId = _invoice.value!!.invoiceId,
                paymentStatus = PaymentStatus.INIT,
                amount = _invoice.value!!.finalPrice
            )
        ).onSuccess {
                setState(UiState.SUCCESS)
                viewController?.payWithCODSuccess()
            }.onError {
                error = SingleEvent(it.message)
                setState(UiState.ERROR)
            }
    }
}