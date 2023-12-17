package com.sudoo.userservice.service.impl;

import com.sudoo.userservice.controller.dto.AddressDto;
import com.sudoo.userservice.repository.AddressRepository;
import com.sudoo.userservice.repository.entitity.Address;
import com.sudoo.userservice.repository.entitity.Location;
import com.sudoo.userservice.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto getAddress(String userId) {
        Address address = addressRepository.getReferenceById(userId);
        return toDto(address);
    }

    @Override
    public void deleteAddress(String userId) {
        addressRepository.deleteById(userId);
    }

    @Override
    public AddressDto putAddress(String userId, AddressDto addressDto) {
        if (addressDto.getAddressId() == null) {
            addressDto.setAddressId(userId);
        }
        Address oldAddress = addressRepository.getReferenceById(userId);
        oldAddress.setProvinceID(addressDto.getProvinceID());
        oldAddress.setDistrictID(addressDto.getDistrictID());
        oldAddress.setWardCode(addressDto.getWardCode());
        oldAddress.setProvinceName(addressDto.getProvinceName());
        oldAddress.setDistrictName(addressDto.getDistrictName());
        oldAddress.setWardName(addressDto.getWardName());
        oldAddress.setAddress(addressDto.getAddress());
        oldAddress.setLocation(addressDto.getLocation());
        addressRepository.save(oldAddress);
        return toDto(oldAddress);
    }

    @Override
    public Location getLocation(String userId) {
        Address address = addressRepository.getReferenceById(userId);
        return address.getLocation();
    }

    @Override
    public AddressDto toDto(Address address) {
        return new AddressDto(
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
    }

    @Override
    public Address toEntity(AddressDto addressDto) {
        return new Address(
                addressDto.getAddressId(),
                addressDto.getProvinceID(),
                addressDto.getDistrictID(),
                addressDto.getWardCode(),
                addressDto.getProvinceName(),
                addressDto.getDistrictName(),
                addressDto.getWardName(),
                addressDto.getAddress(),
                addressDto.getLocation()
        );
    }
}
