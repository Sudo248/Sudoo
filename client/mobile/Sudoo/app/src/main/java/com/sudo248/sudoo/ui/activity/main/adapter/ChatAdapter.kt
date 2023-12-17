package com.sudo248.sudoo.ui.activity.main.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sudo248.base_android.ktx.invisible
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.databinding.ItemChatMeBinding
import com.sudo248.sudoo.databinding.ItemChatOtherBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.chat.Chat
import com.sudo248.sudoo.ui.uimodel.adapter.loadImage
import kotlinx.coroutines.coroutineScope

class ChatAdapter(
    private val clientId: String
) : RecyclerView.Adapter<ChatViewHolder>() {
    companion object {
        const val VIEW_CHAT_ME = 1
        const val VIEW_CHAT_OTHER = 2
    }

    val chats: MutableList<Chat> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(chat: List<Chat>) {
        this.chats.clear()
        this.chats.addAll(chat)
        notifyDataSetChanged()
    }

    suspend fun addMessage(chat: Chat) = coroutineScope {
        chats.add(chat)
        if (chats.size > 1) {
            notifyItemChanged(chats.size - 2)
        }
        notifyItemInserted(chats.size - 1)
    }

    override fun getItemViewType(position: Int): Int = when (chats[position].sender.userId) {
        clientId -> VIEW_CHAT_ME
        else -> VIEW_CHAT_OTHER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return when (viewType) {
            VIEW_CHAT_ME -> {
                ChatMeViewHolder(
                    ItemChatMeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ChatOtherViewHolder(
                    ItemChatOtherBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.onBind(
            chats[position],
            position,
            isSameNext = (position < chats.size - 1 && chats[position].sender.userId == chats[position + 1].sender.userId)
        )
        Log.d("sudoo", "onBindViewHolder: messages: ${chats.size} ")
        if (position < chats.size - 1) {
            Log.d("sudoo", "onBindViewHolder: position: $position")
            Log.d(
                "sudoo",
                "onBindViewHolder: ${position < chats.size - 1} && ${chats[position].sender.userId == chats[position + 1].sender.userId}"
            )
        }
    }

    override fun getItemCount(): Int = chats.size

}

abstract class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(chat: Chat, position: Int, isSameNext: Boolean = false)
}

class ChatMeViewHolder(private val binding: ItemChatMeBinding) :
    ChatViewHolder(binding.root) {
    override fun onBind(chat: Chat, position: Int, isSameNext: Boolean) {
        binding.apply {
            if (isSameNext) {
                root.setPadding(root.paddingLeft, root.paddingTop, root.paddingRight, 0)
            }
            txtContent.text = chat.content
        }
    }
}

class ChatOtherViewHolder(private val binding: ItemChatOtherBinding) :
    ChatViewHolder(binding.root) {
    override fun onBind(chat: Chat, position: Int, isSameNext: Boolean) {
        binding.apply {
            if (isSameNext) {
                cardAvatar.invisible()
                root.setPadding(root.paddingLeft, root.paddingTop, root.paddingRight, 0)
            } else {
                cardAvatar.visible()
                loadImage(imgAvatar, chat.sender.image)
            }
            txtContent.text = chat.content
        }
    }
}