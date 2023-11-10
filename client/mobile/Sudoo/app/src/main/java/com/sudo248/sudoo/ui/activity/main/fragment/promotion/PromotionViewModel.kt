package com.sudo248.sudoo.ui.activity.main.fragment.promotion

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.promotion.Promotion
import com.sudo248.sudoo.domain.repository.PromotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromotionViewModel @Inject constructor(
    private val promotionRepository: PromotionRepository
): BaseViewModel<NavDirections>() {

    private val _promotions = MutableLiveData<List<Promotion>> ()
    val promotions: LiveData<List<Promotion>> = _promotions

    var selectedPromotion: Promotion? = null
        set(value) {
            selectedPromotionId = value?.promotionId
            field = value
        }

    var selectedPromotionId: String? = null

    var error: SingleEvent<String?> = SingleEvent(null)

    fun getPromotions() = launch{
        setState(UiState.LOADING)
        getAllPromotion()
    }

    fun getAllPromotion() = launch {
        promotionRepository.getAllPromotion()
            .onSuccess { promotions ->
                selectedPromotionId?.let {  promotionId ->
                    selectedPromotion = promotions.first { it.promotionId == promotionId }
                }
                _promotions.postValue(promotions)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun applyPromotion() {
        val bundle = Bundle().apply {
            putSerializable(Constants.Key.PROMOTION, selectedPromotion)
        }
        navigator.back(key = Constants.Key.PROMOTION, bundle)
    }

}