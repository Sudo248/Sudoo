package com.sudoo.userservice.service;

import com.sudoo.userservice.controller.dto.AddressDto;
import com.sudoo.userservice.repository.entitity.Address;
import com.sudoo.userservice.repository.entitity.Location;

public interface AddressService {

    AddressDto postAddress(AddressDto addressDto);

    AddressDto getAddress(String userId);
    void deleteAddress(String userId);
    AddressDto putAddress(String userId, AddressDto addressDto);

    Location getLocation(String userId);

    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
