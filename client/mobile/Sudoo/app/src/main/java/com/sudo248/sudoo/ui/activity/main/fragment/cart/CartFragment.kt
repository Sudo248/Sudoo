package com.sudo248.sudoo.ui.activity.main.fragment.cart

import androidx.fragment.app.viewModels
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.FragmentCartBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.ui.activity.main.MainActivity
import com.sudo248.sudoo.ui.activity.main.adapter.CartAdapter
import com.sudo248.sudoo.ui.activity.payment.PaymentActivity
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(), ViewController {
    override val viewModel: CartViewModel by viewModels()
    override val enableStateScreen: Boolean
        get() = true

    private val adapter: CartAdapter by lazy {
        CartAdapter(
            onUpdateItemAmount = {
                viewModel.updateProduct(it)
            },
            onDeleteItem = { addSupplierProduct ->
                DialogUtils.showConfirmDialog(
                    requireContext(),
                    title = "Xóa khỏi giỏ hàng",
                    description = "Bạn có chắc chắn muốn xóa sản phẩm này không?",
                    positive = "OK",
                    onPositive = {
                        viewModel.deleteItemFromCart(addSupplierProduct)
                    }
                )
            }
        )
    }

    override fun initView() {
        viewModel.viewController = this
        binding.rcvItems.adapter = adapter
        binding.refresh.setOnRefreshListener {
            viewModel.getActiveCart()
        }
        viewModel.getActiveCart()
        binding.btnBuyNow.setOnClickListener {
            viewModel.buy()
        }
    }

    override fun observer() {
        super.observer()
        viewModel.cart.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = false
            adapter.submitList(it.cartProducts)
            setBadgeCart(it.cartProducts.size)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.apply {
                txtTotalPrice.text = getString(R.string.total_price, Utils.formatVnCurrency(it))
            }
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

    fun setBadgeCart(amount: Int) {
        (requireActivity() as MainActivity).setBadgeCart(amount)
    }

    override fun navigateToPayment(invoiceId: String) {
        (requireActivity() as MainActivity).navigateTo(PaymentActivity::class.createActionIntentDirections {
            putExtra(Constants.Key.INVOICE_ID, invoiceId)
        })
    }
}