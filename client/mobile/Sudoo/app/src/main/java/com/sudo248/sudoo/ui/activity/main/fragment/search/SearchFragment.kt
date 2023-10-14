package com.sudo248.sudoo.ui.activity.main.fragment.search

import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.sudoo.databinding.FragmentSearchBinding
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.ui.activity.main.adapter.ProductInfoAdapter
import com.sudo248.sudoo.ui.base.LoadMoreRecyclerViewListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()
    override val enableStateScreen: Boolean
        get() = true

    private val productLoadMore = object : LoadMoreRecyclerViewListener {
        override fun onLoadMore(page: Int, itemCount: Int) {

        }
    }

    val productInfoAdapter = ProductInfoAdapter(::onProductItemClick, productLoadMore)

    override fun initView() {
        binding.rcvProduct.adapter = productInfoAdapter
        binding.imgBack.setOnClickListener {
            back()
        }
        binding.search.apply {
            isIconified = false
            requestFocusFromTouch()
        }
        setupSearch()
    }

    private fun setupSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(name = query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun observer() {
        viewModel.products.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.apply {
                    imgNotFound.visible()
                    rcvProduct.gone()
                }
            } else {
                binding.rcvProduct.visible()
                binding.imgNotFound.gone()
                productInfoAdapter.submitList(it)
            }
        }
    }

    private fun onProductItemClick(item: ProductInfo) {
//        navigateTo(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(item))
    }
}