package com.sudoo.userservice.service;

import com.sudoo.userservice.controller.dto.UserDto;
import com.sudoo.userservice.repository.entitity.User;

public interface UserService {
    UserDto getUser(String userId);
    UserDto postUser(UserDto userDto);
    UserDto putUser(String userId, UserDto userDto);
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
