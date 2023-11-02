package com.sudo248.sudoo.ui.activity.ar

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.sudoo.domain.repository.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ArViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : BaseViewModel<IntentDirections>() {
    var error: SingleEvent<String?> = SingleEvent(null)

    private val _modelSource = MutableLiveData<Uri>()
    val modelSource: LiveData<Uri> = _modelSource

    fun getModelResource(parent: File, source: String) = launch {
        emitState(UiState.LOADING)
        storageRepository.getArModelResource(parent, source)
            .onSuccess {
                _modelSource.postValue(it)
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

}