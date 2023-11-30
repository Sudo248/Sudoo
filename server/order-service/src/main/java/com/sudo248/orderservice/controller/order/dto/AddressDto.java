package com.sudo248.orderservice.controller.order.dto;

import com.sudo248.orderservice.repository.entity.order.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String addressId;

    private int provinceID;

    private int districtID;

    private String wardCode;

    private String provinceName;

    private String districtName;

    private String wardName;

    private String address;

    private String fullAddress;
}
