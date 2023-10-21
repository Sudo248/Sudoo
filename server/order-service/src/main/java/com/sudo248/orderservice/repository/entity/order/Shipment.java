package com.sudo248.orderservice.repository.entity.order;

import com.sudo248.orderservice.external.GHNService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Shipment {
    private ShipmentType shipmentType = GHNService.DEFAULT_SHIPMENT_TYPE;
    private int deliveryTime = 0;
    private LocalDateTime receivedDateTime = null;
    private double shipmentPrice = 0.0;
}
