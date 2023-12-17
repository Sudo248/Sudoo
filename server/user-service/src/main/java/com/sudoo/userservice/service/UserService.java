package com.sudoo.userservice.service;

import com.sudo248.domain.exception.ApiException;
import com.sudoo.userservice.controller.dto.UserDto;
import com.sudoo.userservice.controller.dto.UserInfoDto;
import com.sudoo.userservice.repository.entitity.User;

public interface UserService {
    UserDto getUser(String userId);
    UserInfoDto getUserInfo(String userId) throws ApiException;
    UserDto postUser(UserDto userDto);
    UserDto putUser(String userId, UserDto userDto);
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
