package com.sudo248.sudoo.ui.activity.main.fragment.cart

import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentCartBinding
import com.sudo248.sudoo.domain.entity.cart.CartProduct
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.adapter.CartAdapter
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.SudooDialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>() {
    override val viewModel: CartViewModel by viewModels()
    override val enableStateScreen: Boolean
        get() = true

    private val adapter: CartAdapter by lazy {
        CartAdapter(
            onUpdateItemAmount = {
                viewModel.updateProduct(it)
            },
            onDeleteItem = { addSupplierProduct ->
                SudooDialogUtils.showConfirmDialog(
                    requireContext(),
                    title = "Xóa khỏi giỏ hàng",
                    description = "Bạn có chắc chắn muốn xóa sản phẩm này không?",
                    onPositive = {
                        viewModel.deleteItemFromCart(addSupplierProduct)
                    }
                )
            },
            onCheckedChangeItem = { isChecked, cartProduct ->
                viewModel.onClickCheckBox(isChecked, cartProduct)
            }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.rcvItems.adapter = adapter
        binding.refresh.setOnRefreshListener {
            viewModel.getActiveCart()
        }
        viewModel.getActiveCart()
        binding.btnBuyNow.setOnClickListener {
            viewModel.buy()
        }

        binding.txtGoToBuy.setOnClickListener {
            navigateOffAll(CartFragmentDirections.actionCartFragmentToDiscoveryFragment())
        }
    }

    override fun observer() {
        super.observer()
        viewModel.cart.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = false
            performCartItem(it.cartProducts)
            setBadgeCart(it.cartProducts.size)
        }
    }

    private fun performCartItem(items: List<CartProduct>) {
        if (items.isEmpty()) {
            binding.constraintCart.gone()
            binding.lnCartEmpty.visible()
        } else {
            binding.constraintCart.visible()
            binding.lnCartEmpty.gone()
            adapter.submitList(items)
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

    private fun setBadgeCart(amount: Int) {
        (requireActivity() as MainActivity).setBadgeCart(amount)
    }
}