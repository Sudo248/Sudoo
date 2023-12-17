package com.sudoo.notification.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.sudoo.notification.repository.TopicRepository;
import com.sudoo.notification.repository.UserRepository;
import com.sudoo.notification.repository.entity.Notification;
import com.sudoo.notification.repository.entity.Topic;
import com.sudoo.notification.repository.entity.User;
import com.sudoo.notification.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationImpl implements NotificationService {

    private final UserRepository userRepository;

    private final TopicRepository topicRepository;

    private final FirebaseMessaging firebaseMessaging;

    public NotificationImpl(UserRepository userRepository, TopicRepository topicRepository, FirebaseMessaging firebaseMessaging) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.firebaseMessaging = firebaseMessaging;
    }

    @Override
    public String sendNotificationToTopic(String userId, String topic, Notification notification) {
        Message message = Message.builder().setTopic(topic).setNotification(notification.toFirebaseNotification()).build();
        firebaseMessaging.sendAsync(message);
        topicRepository.save(new Topic(topic));
        return "Success";
    }

    @Override
    public String sendNotification(String userId, String otherId, Notification notification) {
        Optional<User> user = userRepository.get(otherId);
        return user.map(value -> sendNotificationToToken(value.getToken(), notification)).orElse("Not found user");
    }

    @Override
    public String sendNotificationToToken(String token, Notification notification) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification.toFirebaseNotification())
                .build();

        firebaseMessaging.sendAsync(message);
        return "Success";
    }

    @Override
    public boolean saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean saveToken(String userId, String token) {
        Optional<User> user = userRepository.get(userId);
        if (user.isPresent()) {
            User userValue = user.get();
            userValue.setToken(token);
            return userRepository.save(userValue);
        }
        return false;
    }

    @Override
    public String sendNotificationPayment(String userId, Notification notification) {
        Optional<User> user = userRepository.get(userId);
        if (user.isPresent() && user.get().getToken() != null) {
            return sendNotificationToToken(user.get().getToken(), notification);
        }
        return "Not found user";
    }
}
