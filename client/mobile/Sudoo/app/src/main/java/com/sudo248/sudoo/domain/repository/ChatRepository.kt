package com.sudo248.sudoo.domain.repository

import android.provider.ContactsContract.RawContacts.Data
import com.sudo248.base_android.core.DataState
import com.sudo248.sudoo.domain.entity.chat.Chat
import com.sudo248.sudoo.domain.entity.chat.Conversation

interface ChatRepository {
    suspend fun getConversation(supplierId: String): DataState<Conversation, Exception>
    suspend fun sendMessageToSupplier(supplierId: String, content: String): DataState<Chat, Exception>
}