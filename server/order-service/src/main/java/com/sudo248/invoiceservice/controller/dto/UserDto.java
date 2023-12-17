package com.sudo248.invoiceservice.controller.dto;

import com.sudo248.invoiceservice.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;

    private String fullName;

    private String phone;

    private Date dob;

    private String bio;

    private String avatar;

    private String cover;

    private AddressDto address;

    private Gender gender;
}
