package com.sudo248.sudoo.ui.activity.main.fragment.user

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sudo248.base_android.ktx.setTransparentBackground
import com.sudo248.sudoo.databinding.FragmentChooseAddressBinding
import com.sudo248.sudoo.ui.activity.main.adapter.AddressSuggestionAdapter
import com.sudo248.sudoo.ui.uimodel.StepChooseAddress

class ChooseAddressBottomSheetFragment private constructor(
    private val viewModel: UserViewModel
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentChooseAddressBinding
    private val adapter: AddressSuggestionAdapter by lazy {
        AddressSuggestionAdapter {
            viewModel.onChooseAddress(address = it)
        }
    }

    companion object {
        const val TAG = "ChooseAddressBottomSheetFragment"
        fun newInstance(viewModel: UserViewModel): ChooseAddressBottomSheetFragment {
            return ChooseAddressBottomSheetFragment(viewModel)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            bottomSheetDialog.setCancelable(false)
            bottomSheetDialog.setTransparentBackground()
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                behaviour.isDraggable = false
                val layoutParams = bottomSheet.layoutParams
//                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height =
                    (Resources.getSystem().displayMetrics.heightPixels * 0.9f).toInt()
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseAddressBinding.inflate(inflater)
        setupUi()
        observer()
        return binding.root
    }

    private fun setupUi() {
        binding.rcvAddress.adapter = adapter
        binding.imgClose.setOnClickListener {
            dismiss()
        }
    }

    private fun observer() {
        viewModel.stepChooseAddress.observe(viewLifecycleOwner) {
            if (it == StepChooseAddress.CLOSE) {
                dismiss()
            } else {
                binding.txtTitle.text = getString(it.value)
            }
        }

        viewModel.suggestionAddress.observe(viewLifecycleOwner) { addressSuggestions ->
            binding.rcvAddress.scrollToPosition(0)
            adapter.submitList(addressSuggestions.sortedBy { it.addressName })
        }
    }

}