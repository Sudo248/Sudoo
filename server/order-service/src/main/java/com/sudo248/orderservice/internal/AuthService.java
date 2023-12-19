package com.sudo248.orderservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.orderservice.repository.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "auth-service")
@Service
public interface AuthService {
    @GetMapping("/api/v1/auth/internal/accounts/role")
    ResponseEntity<BaseResponse<Role>> getRoleByUserId(@RequestHeader(Constants.HEADER_USER_ID) String userId);
}
