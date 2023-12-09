package com.sudo248.sudoo.ui.activity.main

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:15 - 23/02/2023
 */


@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationService: FusedLocationProviderClient,
    private val cartRepository: CartRepository
) : BaseViewModel<IntentDirections>() {

    init {
        getItemInCart()
    }

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> = _imageUri

    private var tmpImageUri: Uri? = null

    private val _countCartItem = MutableLiveData(0)
    val countCartItem: LiveData<Int> = _countCartItem

    var viewController: ViewController? = null

    fun setImageUri(uri: Uri?) {
        _imageUri.postValue(uri)
    }

    fun pickImage() {
        viewController?.pickImage()
    }

    fun takeImage() {
        viewController?.createTempPictureUri()?.let {
            tmpImageUri = it
            viewController?.takeImage(it)
        } ?: throw NullPointerException("Can't create temp picture uri")
    }

    fun getTakeImageUri(): Uri? {
        _imageUri.value = tmpImageUri
        return tmpImageUri
    }

    fun requestPermission(permission: String, callback: (Boolean) -> Unit) {
        viewController?.requestPermission(permission, callback)
    }

    fun getCurrentLocation() = launch {
        Constants.location = _getCurrentLocation()
    }

    @SuppressLint("MissingPermission")
    private suspend fun _getCurrentLocation(): String = suspendCoroutine { continuation ->
        locationService.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnCompleteListener {
                it.result.run {
                    try {
                        continuation.resume("$longitude,$latitude")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        continuation.resume("")
                    }
                }
            }

    }

    fun getItemInCart() = launch {
        cartRepository.countItemInActiveCart()
            .onSuccess {
                _countCartItem.postValue(it)
            }.onError {
                _countCartItem.postValue(0)
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewController = null
    }

}