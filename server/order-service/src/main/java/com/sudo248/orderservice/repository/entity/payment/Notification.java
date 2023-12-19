package com.sudo248.orderservice.repository.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String image;
    private String title;
    private String body;
}
