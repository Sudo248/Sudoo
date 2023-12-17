package com.sudo248.invoiceservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Shipment {
    private String nameShipment = "Nhanh";
    private int timeShipment = 0;
    private String timeUnit = "";
    private double priceShipment = 0.0;
}
