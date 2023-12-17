package com.sudoo.notification.service;

import com.sudoo.notification.repository.entity.Notification;
import com.sudoo.notification.repository.entity.User;

public interface NotificationService {

    String promotionTopic = "public.promotion";

    String sendNotificationToTopic(String userId, String topic, Notification notification);
    String sendNotificationToToken(String token, Notification notification);

    String sendNotificationPayment(String userId, Notification notification);

    String sendNotification(String userId, String otherId, Notification notification);

    boolean saveUser(User user);

    boolean saveToken(String userId, String token);

}
