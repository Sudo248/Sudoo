package com.sudoo.userservice.service;

import com.sudo248.domain.exception.ApiException;
import com.sudoo.userservice.controller.dto.AddressSuggestionDto;

import java.util.List;

public interface AddressSuggestionService {

    List<AddressSuggestionDto> getProvinces() throws ApiException;

    List<AddressSuggestionDto> getDistricts(int provinceId) throws ApiException;

    List<AddressSuggestionDto> getWards(int districtId) throws ApiException;

}
