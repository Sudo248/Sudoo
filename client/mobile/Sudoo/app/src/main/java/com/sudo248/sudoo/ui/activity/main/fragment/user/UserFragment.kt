package com.sudo248.sudoo.ui.activity.main.fragment.user

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.navigation.IntentDirections
import com.sudo248.base_android.navigation.Navigator
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentUserBinding
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.MainViewModel
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {
    override val viewModel: UserViewModel by activityViewModels()
    private val mainActivityViewModel: MainViewModel by activityViewModels()
    override val enableStateScreen: Boolean = true

    override fun initView() {
        binding.viewModel = viewModel
        viewModel.setActivityViewModel(mainActivityViewModel)
    }

    override fun onStateError() {
        super.onStateError()
        viewModel.error.run {
            val message = getValueIfNotHandled()
            if (!message.isNullOrEmpty()) {
                DialogUtils.showErrorDialog(
                    context = requireContext(),
                    message = message
                )
            }
        }
    }
}