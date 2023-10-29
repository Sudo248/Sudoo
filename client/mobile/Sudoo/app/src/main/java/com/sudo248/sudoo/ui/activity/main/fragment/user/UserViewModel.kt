package com.sudo248.sudoo.ui.activity.main.fragment.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.bindUiState
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.user.AddressSuggestion
import com.sudo248.sudoo.domain.repository.AuthRepository
import com.sudo248.sudoo.domain.repository.FileRepository
import com.sudo248.sudoo.domain.repository.UserRepository
import com.sudo248.sudoo.ui.activity.auth.AuthActivity
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.mapper.toUser
import com.sudo248.sudoo.ui.mapper.toUserUi
import com.sudo248.sudoo.ui.uimodel.StepChooseAddress
import com.sudo248.sudoo.ui.uimodel.UserUiModel
import com.sudo248.sudoo.ui.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val fileRepository: FileRepository
) : BaseViewModel<NavDirections>() {

    private var viewController: ViewController? = null

    private val _stepChooseAddress = MutableLiveData(StepChooseAddress.PROVINCE)
    val stepChooseAddress: LiveData<StepChooseAddress> = _stepChooseAddress

    private val _suggestionAddress = MutableLiveData<List<AddressSuggestion>>()
    val suggestionAddress: LiveData<List<AddressSuggestion>> = _suggestionAddress

    private val _user = MutableLiveData(UserUiModel())
    val user: LiveData<UserUiModel> = _user

    private var isUpdateLocation = false

    var error: SingleEvent<String?> = SingleEvent(null)

    private lateinit var parentViewModel: MainViewModel

    init {
        getUserInfo()
    }

    fun setActivityViewModel(parentViewModel: MainViewModel) {
        this.parentViewModel = parentViewModel
    }

    fun setViewController(viewController: ViewController) {
        this.viewController = viewController
    }

    private fun getUserInfo() = launch {
        emitState(UiState.LOADING)
        userRepository.getUserInfo()
            .onSuccess {
                _user.postValue(it.toUserUi())
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    fun logout() = launch {
        authRepository.logout()
            .onSuccess {
                SharedPreferenceUtils.putString(Constants.Key.TOKEN, "")
                navigateToAuthActivity()
            }
            .onError {
                error = SingleEvent(it.message)
            }.bindUiState(_uiState)
    }

    private fun navigateToAuthActivity() {
        parentViewModel.navigator()
            .navigateOff(AuthActivity::class.createActionIntentDirections())
    }

    fun showDialogDatePicker() {
        viewController?.showDialogDatePicker()
    }

    fun selectDate(selection: Long?) {
        _user.value?.dob?.set(Utils.formatDob(Utils.localDateFrom(selection ?: 0L)))
    }

    fun updateUser() = launch {
        setState(UiState.LOADING)
        parentViewModel.imageUri.value?.let {
            viewController?.run {
                val imageUrl = fileRepository.uploadImage(getPathImageFromUri(it)).get()
                _user.value?.avatar?.set(imageUrl)
            }
        }
        userRepository.updateUser(_user.value!!.toUser(), isUpdateLocation)
            .onSuccess {
                setState(UiState.SUCCESS)
                parentViewModel.setImageUri(null)
                _user.value?.address?.run {
                    fullAddress.set("${address.get()}, ${wardName.get()}, ${districtName.get()}, ${provinceName.get()}")
                }
                navigator.back()
            }
            .onError {
                viewController?.toast(it.message ?: Constants.UNKNOWN_ERROR)
                setState(UiState.ERROR)
            }
    }

    fun navigateToEditFragment() {
        navigator.navigateTo(UserFragmentDirections.actionUserFragmentToUserEditFragment())
    }

    fun getAddressProvinceSuggestion() = launch {
        viewController?.showBottomSheetChooseAddress()
        userRepository.getAddressSuggestionProvince()
            .onSuccess {
                _suggestionAddress.postValue(it)
            }
            .onError {
                viewController?.to(it.message)
            }
    }

    private fun getAddressSuggestionDistrict(provinceId: Int) = launch {
        userRepository.getAddressSuggestionDistrict(provinceId)
            .onSuccess {
                _suggestionAddress.postValue(it)
            }
            .onError {
                viewController?.to(it.message)
            }
    }

    private fun getAddressSuggestionWard(districtId: Int) = launch {
        userRepository.getAddressSuggestionWard(districtId)
            .onSuccess {
                _suggestionAddress.postValue(it)
            }
            .onError {
                viewController?.to(it.message)
            }
    }

    fun onChooseAddress(address: AddressSuggestion) {
        isUpdateLocation = true
        stepChooseAddress.value?.run {
            when (this) {
                StepChooseAddress.PROVINCE -> {
                    _user.value?.address?.apply {
                        provinceID = address.addressId
                        provinceName.set(address.addressName)
                    }
                    getAddressSuggestionDistrict(address.addressId)
                }
                StepChooseAddress.DISTRICT -> {
                    _user.value?.address?.apply {
                        districtID = address.addressId
                        districtName.set(address.addressName)
                    }
                    getAddressSuggestionWard(address.addressId)
                }
                StepChooseAddress.WARD -> {
                    _user.value?.address?.apply {
                        wardCode = address.addressCode
                        wardName.set(address.addressName)
                    }
                }
                else -> {

                }
            }

            _stepChooseAddress.postValue(nextStep())
        }
    }

    fun pickImage() {
        viewController?.pickImage()
    }

    fun back() {
        navigator.back()
    }

    fun navigateToReviewList() {
        navigator.navigateTo(UserFragmentDirections.actionUserFragmentToReviewListFragment())
    }

    override fun onCleared() {
        super.onCleared()
        viewController = null
        parentViewModel.setImageUri(null)
    }
}