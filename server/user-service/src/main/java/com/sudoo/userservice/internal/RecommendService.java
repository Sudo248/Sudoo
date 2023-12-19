package com.sudoo.userservice.internal;

import com.sudoo.userservice.controller.dto.RecommendUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "RecommendService", url = "http://recommend-service:5000/api/v1")
@Service
public interface RecommendService {
    @PostMapping("/users")
    void upsertUser(@RequestBody RecommendUserDto recommendUser);
}
