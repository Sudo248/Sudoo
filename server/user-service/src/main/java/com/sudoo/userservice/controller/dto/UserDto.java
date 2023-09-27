package com.sudoo.userservice.controller.dto;

import com.sudoo.userservice.repository.entitity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;

    private String fullName;

    private String phone;

    private LocalDate dob;

    private String bio;

    private String avatar;

    private String cover;

    private AddressDto address;

    private Gender gender;
}
