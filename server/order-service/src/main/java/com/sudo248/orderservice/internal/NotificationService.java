package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.orderservice.repository.entity.payment.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "NOTIFICATION-SERVICE")
public interface NotificationService {
    @PostMapping("/api/v1/notification/send/payment")
    ResponseEntity<BaseResponse<?>> sendNotificationPaymentStatus(@RequestHeader(Constants.HEADER_USER_ID) String userId, @RequestBody Notification notification);
}
