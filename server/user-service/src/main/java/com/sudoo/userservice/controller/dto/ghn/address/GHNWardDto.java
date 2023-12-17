package com.sudoo.userservice.controller.dto.ghn.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sudoo.userservice.controller.dto.AddressSuggestionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GHNWardDto {
    @JsonProperty("WardCode")
    public String wardCode;
    @JsonProperty("DistrictID")
    public int districtID;
    @JsonProperty("WardName")
    public String wardName;
    @JsonProperty("NameExtension")
    public ArrayList<String> nameExtension;
    @JsonProperty("IsEnable")
    public int isEnable;
    @JsonProperty("CanUpdateCOD")
    public boolean canUpdateCOD;
    @JsonProperty("UpdatedBy")
    public int updatedBy;
    @JsonProperty("CreatedAt")
    public String createdAt;
    @JsonProperty("UpdatedAt")
    public String updatedAt;
    @JsonProperty("SupportType")
    public int supportType;
    @JsonProperty("PickType")
    public int pickType;
    @JsonProperty("DeliverType")
    public int deliverType;
    @JsonProperty("WhiteListClient")
    public Object whiteListClient;
    @JsonProperty("WhiteListWard")
    public Object whiteListWard;
    @JsonProperty("Status")
    public int status;
    @JsonProperty("ReasonCode")
    public String reasonCode;
    @JsonProperty("ReasonMessage")
    public String reasonMessage;
    @JsonProperty("OnDates")
    public Object onDates;

    public AddressSuggestionDto toAddressSuggestionDto() {
        return new AddressSuggestionDto(
                -1,
                wardName,
                wardCode
        );
    }
}
