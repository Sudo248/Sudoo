package com.sudoo.userservice.controller;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.common.Constants;
import com.sudo248.domain.util.Utils;
import com.sudoo.userservice.controller.dto.AddressDto;
import com.sudoo.userservice.repository.entitity.Location;
import com.sudoo.userservice.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<?>> postAddress(
            @RequestBody AddressDto addressDto
    ) {
        return Utils.handleException(() -> {
            AddressDto savedAddressDto = addressService.postAddress(addressDto);
            return BaseResponse.ok(savedAddressDto);
        });
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<BaseResponse<?>> getAddressById(
            @PathVariable("addressId") String addressId
    ) {
        return Utils.handleException(() -> {
            AddressDto addressDto = addressService.getAddress(addressId);
            return BaseResponse.ok(addressDto);
        });
    }

    @PutMapping
    public ResponseEntity<BaseResponse<?>> putAddress(
            @RequestBody AddressDto addressDto
    ) {
        return Utils.handleException(()->{
            AddressDto _addressDto = addressService.putAddress(addressDto);
            return BaseResponse.ok(_addressDto);
        });
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<BaseResponse<?>> deleteAddress(
            @PathVariable("addressId") String addressId
    ) {
        return Utils.handleException(()->{
            addressService.deleteAddress(addressId);
            return BaseResponse.ok("Deleted address " + addressId);
        });
    }

    @GetMapping("/internal/location")
    public Location getInternalLocation(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) {
        return addressService.getLocation(userId);
    }
}
