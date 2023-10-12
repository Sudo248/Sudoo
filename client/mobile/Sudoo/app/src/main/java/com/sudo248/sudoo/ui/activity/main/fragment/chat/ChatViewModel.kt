package com.sudo248.sudoo.ui.activity.main.fragment.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import com.google.firebase.messaging.FirebaseMessaging
import com.sudo248.base_android.base.BaseViewModel
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.ktx.onError
import com.sudo248.base_android.ktx.onSuccess
import com.sudo248.base_android.utils.SharedPreferenceUtils
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.domain.entity.chat.Chat
import com.sudo248.sudoo.domain.entity.chat.Conversation
import com.sudo248.sudoo.domain.repository.ChatRepository
import com.sudo248.sudoo.ui.service.FirebaseMessageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : BaseViewModel<NavDirections>() {

    private val _conversation = MutableLiveData<Conversation>()
    val conversation: LiveData<Conversation> = _conversation

    private lateinit var supplierId: String

    private lateinit var topic: String

    fun setup(supplierId: String) {
        this.supplierId = supplierId
        this.topic = "chat%$supplierId%$clientId"
    }

    val clientId = SharedPreferenceUtils.getString(Constants.Key.USER_ID)

    var error: SingleEvent<String?> = SingleEvent(null)

    init {
        collectChatFlow()
    }

    private fun collectChatFlow() = launch {
        FirebaseMessageService.chatFlow.collect {
            val chats = mutableListOf<Chat>().apply {
                addAll(conversation.value?.chats ?: listOf())
                add(it)
            }
            _conversation.postValue(_conversation.value?.copy(chats = chats))
        }
    }

    fun getConversation() = launch {
        emitState(UiState.LOADING)
        chatRepository.getConversation(supplierId)
            .onSuccess {
                _conversation.postValue(it)
                emitState(UiState.SUCCESS)
            }
            .onError {
                error = SingleEvent(it.message)
                emitState(UiState.ERROR)
            }
    }

    fun sendMessage(content: String) = launch {
        chatRepository.sendMessageToSupplier(supplierId, content)
            .onSuccess {
                val chats = mutableListOf<Chat>().apply {
                    addAll(conversation.value?.chats ?: listOf())
                    add(it)
                }
                _conversation.postValue(_conversation.value?.copy(chats = chats))
            }
            .onError {
                error = SingleEvent(it.message)
                emitState(UiState.ERROR)
            }
    }

    fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
    }

    override fun onCleared() {
        super.onCleared()
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
    }

}