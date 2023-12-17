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
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<?>> getAddress(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) {
        return Utils.handleException(() -> {
            AddressDto addressDto = addressService.getAddress(userId);
            return BaseResponse.ok(addressDto);
        });
    }

    @GetMapping("/{otherId}")
    public ResponseEntity<BaseResponse<?>> getOtherAddress(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestParam("otherId") String otherId
    ) {
        return Utils.handleException(() -> {
            AddressDto addressDto = addressService.getAddress(otherId);
            return BaseResponse.ok(addressDto);
        });
    }

    @PutMapping
    public ResponseEntity<BaseResponse<?>> putAddress(
            @RequestHeader(Constants.HEADER_USER_ID) String userId,
            @RequestBody AddressDto addressDto
    ) {
        return Utils.handleException(()->{
            AddressDto _addressDto = addressService.putAddress(userId, addressDto);
            return BaseResponse.ok(_addressDto);
        });
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> deleteAddress(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) {
        return Utils.handleException(()->{
            addressService.deleteAddress(userId);
            return BaseResponse.ok("Deleted address of user " + userId);
        });
    }

    @GetMapping("/internal/location")
    public Location getInternalLocation(
            @RequestHeader(Constants.HEADER_USER_ID) String userId
    ) {
        return addressService.getLocation(userId);
    }
}
