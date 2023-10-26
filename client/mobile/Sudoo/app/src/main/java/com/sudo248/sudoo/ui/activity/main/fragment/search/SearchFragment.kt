package com.sudo248.sudoo.ui.activity.main.fragment.search

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.KeyBoardUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentSearchBinding
import com.sudo248.sudoo.ui.ktx.requestFocusAndKeyBoard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()
    override val enableStateScreen: Boolean = true
    private val args: SearchFragmentArgs by navArgs()

    override fun initView() {
        viewModel.setCategoryId(args.categoryId)
        binding.rcvProduct.adapter = viewModel.productInfoAdapter
        binding.imgBack.setOnClickListener { back() }
        binding.refresh.setOnRefreshListener { viewModel.refresh() }
        setupSearch()
    }

    private fun setupSearch() {
        if (args.categoryId == null) {
            binding.tvSearch.requestFocusAndKeyBoard()
        }

        binding.tvSearch.hint =
            if (args.categoryName == null) getString(R.string.search) else getString(
                R.string.search_in,
                args.categoryName
            )
        binding.tvSearch.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.search(name = text.toString())
        })
    }

    override fun observer() {
        viewModel.isEmptyProduct.observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    imgNotFound.visible()
                    rcvProduct.gone()
                }
            } else {
                binding.rcvProduct.visible()
                binding.imgNotFound.gone()
            }
        }

        viewModel.isRefresh.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = it
        }
    }
}