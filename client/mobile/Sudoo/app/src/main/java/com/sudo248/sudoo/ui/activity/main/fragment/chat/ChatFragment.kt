package com.sudo248.sudoo.ui.activity.main.fragment.chat

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.sudo248.base_android.base.BaseFragment
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.databinding.FragmentChatBinding
import com.sudo248.sudoo.ui.activity.main.adapter.ChatAdapter
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {
    override val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()
    override val enableStateScreen: Boolean
        get() = true

    private val chatAdapter: ChatAdapter by lazy {
        ChatAdapter(viewModel.clientId).apply {
            registerAdapterDataObserver(autoScroll)
        }
    }

    private val autoScroll = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            scroll()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            scroll()
        }

        override fun onChanged() {
            super.onChanged()
            scroll()
        }
    }

    private fun scroll() {
        lifecycleScope.launch {
            delay(100)
            binding.rcvChat.scrollToPosition(chatAdapter.chats.size - 1)
        }
    }

    override fun initView() {
        viewModel.setup(args.supplierId)
        viewModel.getConversation()
        viewModel.subscribeTopic()
        binding.rcvChat.adapter = chatAdapter
        binding.apply {
            imgBack.setOnClickListener {
                back()
            }

            imgSend.setOnClickListener {
                viewModel.sendMessage(edtInputMessage.text.toString())
                edtInputMessage.text.clear()
            }
        }
    }

    override fun observer() {
        super.observer()
        viewModel.conversation.observe(viewLifecycleOwner) {
            binding.apply {
                loadImage(imgAvatar, it.conversationImage)
                txtTitleChat.text = it.conversationName
                chatAdapter.submitList(it.chats)
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
}