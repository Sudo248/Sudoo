package com.sudoo.userservice.service;

import com.sudo248.domain.exception.ApiException;
import com.sudoo.userservice.controller.dto.AddressDto;
import com.sudoo.userservice.repository.entitity.Address;

public interface AddressService {

    AddressDto postAddress(AddressDto addressDto);

    AddressDto getAddress(String addressId);
    void deleteAddress(String addressId);
    AddressDto putAddress(AddressDto addressDto) throws ApiException;

    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
