package com.sudoo.userservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudoo.userservice.controller.dto.FirebaseUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "CHAT-SERVICE")
public interface FirebaseService {

    @PostMapping("/api/v1/chat/user")
    ResponseEntity<BaseResponse<?>> upsertFirebaseUser(@RequestBody FirebaseUserDto user);

}
