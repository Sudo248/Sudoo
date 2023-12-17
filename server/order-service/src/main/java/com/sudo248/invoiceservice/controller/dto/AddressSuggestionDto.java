package com.sudo248.invoiceservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSuggestionDto {
    private int addressId;
    private String addressName;
    private String addressCode;
}
