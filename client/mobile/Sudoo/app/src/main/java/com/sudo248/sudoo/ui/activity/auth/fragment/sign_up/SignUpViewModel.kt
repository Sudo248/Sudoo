package com.sudo248.sudoo.ui.activity.auth.fragment.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.auth.AuthConfig
import com.sudo248.sudoo.domain.repository.AuthRepository
import com.sudo248.sudoo.ui.activity.auth.AuthViewModel
import com.sudo248.sudoo.ui.activity.otp.OtpActivity
import com.sudo248.sudoo.ui.mapper.toAccount
import com.sudo248.sudoo.ui.uimodel.AccountUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 09:10 - 06/03/2023
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<NavDirections>() {
    private var parentViewModel: AuthViewModel? = null

    private var authConfig: Deferred<AuthConfig> = async { authRepository.getAuthConfig().get() }
    val accountUiModel: AccountUiModel = AccountUiModel()
    val confirmPassword: MutableLiveData<String> = MutableLiveData()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    lateinit var gotoSignIn: () -> Unit

    fun setParentViewModel(viewModel: AuthViewModel) {
        parentViewModel = viewModel
    }

    fun signUp() = launch {
        parentViewModel?.setState(UiState.LOADING)
        val account = accountUiModel.toAccount()
        authRepository.signUp(account)
            .onSuccess {
                parentViewModel?.setState(UiState.SUCCESS)
                if (authConfig.await().enableOtp) {
                    parentViewModel?.navigator()
                        ?.navigateTo(OtpActivity::class.createActionIntentDirections {
                            putExtra(Constants.Key.PHONE_NUMBER, account.phoneNumber)
                        })
                } else {
                    gotoSignIn()
                }
            }
            .onError {
                _error.postValue(it.message)
                accountUiModel.password.set("")
                confirmPassword.postValue("")
                parentViewModel?.setState(UiState.ERROR)
            }
    }

    fun onConfirmPasswordTextChange(text: CharSequence) {
        if (text.toString() != accountUiModel.password.get()) {
            _error.postValue("Incorrect password")
        } else {
            _error.postValue("")
        }
    }
}