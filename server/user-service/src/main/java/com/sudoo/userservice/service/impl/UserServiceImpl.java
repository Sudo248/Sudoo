package com.sudoo.userservice.service.impl;

import com.sudo248.domain.util.Utils;
import com.sudoo.userservice.controller.dto.AddressDto;
import com.sudoo.userservice.controller.dto.FirebaseUserDto;
import com.sudoo.userservice.controller.dto.UserDto;
import com.sudoo.userservice.internal.FirebaseService;
import com.sudoo.userservice.repository.UserRepository;
import com.sudoo.userservice.repository.entitity.Address;
import com.sudoo.userservice.repository.entitity.User;
import com.sudoo.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final FirebaseService firebaseService;

    public UserServiceImpl(UserRepository userRepository, FirebaseService firebaseService) {
        this.userRepository = userRepository;
        this.firebaseService = firebaseService;
    }

    @Override
    public UserDto getUser(String userId) {
        User user = userRepository.getReferenceById(userId);
        return toDto(user);
    }

    @Override
    public UserDto postUser(UserDto userDto) {
        log.error("Sudoo " + userDto);
        User user = toEntity(userDto);
//        log.error("Sudoo " + user);
        userRepository.save(user);
        firebaseService.upsertFirebaseUser(createUserFirebaseFromUser(user));
        return toDto(user);
    }

    @Override
    public UserDto putUser(String userId, UserDto userDto) {
        userDto.setUserId(userId);

        User user = userRepository.getReferenceById(userId);
        user.setFullName(userDto.getFullName());
        user.setDob(userDto.getDob());
        user.setBio(userDto.getBio());
        user.setAvatar(userDto.getAvatar());
        user.setCover(userDto.getCover());
        user.setGender(userDto.getGender());

        AddressDto addressDto = userDto.getAddress();

        Address address = user.getAddress();
        address.setProvinceID(addressDto.getProvinceID());
        address.setDistrictID(addressDto.getDistrictID());
        address.setWardCode(addressDto.getWardCode());
        address.setProvinceName(addressDto.getProvinceName());
        address.setDistrictName(addressDto.getDistrictName());
        address.setWardName(addressDto.getWardName());
        address.setAddress(addressDto.getAddress());
        address.setLocation(addressDto.getLocation());

        user.setAddress(address);
        userRepository.save(user);
        firebaseService.upsertFirebaseUser(createUserFirebaseFromUser(user));
        return toDto(user);
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setFullName(user.getFullName());
        userDto.setPhone(user.getPhone());
        userDto.setDob(user.getDob());
        userDto.setBio(user.getBio());
        userDto.setAvatar(user.getAvatar());
        userDto.setCover(user.getCover());
        userDto.setGender(user.getGender());
        Address address = user.getAddress();
        AddressDto addressDto = new AddressDto(
                address.getAddressId(),
                address.getProvinceID(),
                address.getDistrictID(),
                address.getWardCode(),
                address.getProvinceName(),
                address.getDistrictName(),
                address.getWardName(),
                address.getAddress(),
                address.getLocation(),
                address.getFullAddress()
        );
        userDto.setAddress(addressDto);
        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(Utils.createIdOrElse(userDto.getUserId()));
        user.setFullName(userDto.getFullName());
        user.setPhone(userDto.getPhone());
        user.setDob(userDto.getDob() != null ? userDto.getDob() : LocalDate.now());
        user.setBio(userDto.getBio());
        user.setAvatar(userDto.getAvatar());
        user.setCover(userDto.getCover());
        user.setGender(userDto.getGender());
        AddressDto addressDto = userDto.getAddress();

        Address address = new Address(
                user.getUserId(),
                addressDto.getProvinceID(),
                addressDto.getDistrictID(),
                addressDto.getWardCode(),
                addressDto.getProvinceName(),
                addressDto.getDistrictName(),
                addressDto.getWardName(),
                addressDto.getAddress(),
                addressDto.getLocation(),
                user
        );
        user.setAddress(address);

        return user;
    }

    private FirebaseUserDto createUserFirebaseFromUser(User user) {
        return new FirebaseUserDto(
                user.getUserId(),
                user.getFullName(),
                user.getAvatar()
        );
    }
}
