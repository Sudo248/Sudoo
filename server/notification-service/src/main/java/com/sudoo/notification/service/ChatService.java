package com.sudoo.notification.service;


import com.sudoo.notification.controller.dto.ChatDto;
import com.sudoo.notification.repository.entity.Chat;
import com.sudoo.notification.repository.entity.Conversation;
import com.sudoo.notification.repository.entity.ConversationInfo;
import com.sudoo.notification.repository.entity.User;

import java.util.List;

public interface ChatService {

    static final String conversationTopic = "public.conversation";

    List<ConversationInfo> getAllConversationInfo(String userId);

    Conversation getConversationByTopic(String userId, String topic);

    Chat sendMessageToConversation(String userId, String topic, ChatDto chatDto) throws Exception;

    User upsertUser(User user);

}