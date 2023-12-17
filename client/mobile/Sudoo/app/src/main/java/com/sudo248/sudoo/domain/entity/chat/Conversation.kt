package com.sudo248.sudoo.domain.entity.chat

data class Conversation(
    val topic: String,
    val conversationName: String,
    val conversationImage: String,
    val firstUserId: String,
    val secondUserId: String,
    val chats: List<Chat> = listOf(),
    val updateTime: Long,
)