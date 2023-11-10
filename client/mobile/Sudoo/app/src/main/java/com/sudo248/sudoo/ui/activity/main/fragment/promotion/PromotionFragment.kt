package com.sudo248.sudoo.ui.activity.main.fragment.promotion

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentPromotionBinding
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.adapter.PromotionAdapter
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PromotionFragment : BaseFragment<FragmentPromotionBinding, PromotionViewModel>() {
    override val viewModel: PromotionViewModel by viewModels()
    private val args: PromotionFragmentArgs by navArgs()
    override val enableStateScreen: Boolean
        get() = true

    private lateinit var adapter: PromotionAdapter

    override fun initView() {
        viewModel.selectedPromotionId = args.selectedPromotionId
        if (!args.forChoosePromotion) {
            binding.txtAgree.gone()
        } else {
            (activity as MainActivity).goneBottomNav()
        }
        adapter = PromotionAdapter(viewModel.selectedPromotionId) {
            viewModel.selectedPromotion = it
        }
        binding.rcvPromotion.adapter = adapter
        binding.txtAgree.setOnClickListener {
            viewModel.applyPromotion()
        }
        binding.refresh.setOnRefreshListener {
            viewModel.getAllPromotion()
        }
        viewModel.getPromotions()
    }

    override fun observer() {
        super.observer()
        viewModel.promotions.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = false
            adapter.submitList(it)
        }
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