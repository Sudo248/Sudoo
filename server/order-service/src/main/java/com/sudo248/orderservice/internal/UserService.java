package com.sudo248.orderservice.internal;


import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.orderservice.controller.order.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user-service")
@Service
public interface UserService {
    @GetMapping("/api/v1/users")
    ResponseEntity<BaseResponse<UserDto>> getUser(@RequestHeader(Constants.HEADER_USER_ID) String userId);

}
