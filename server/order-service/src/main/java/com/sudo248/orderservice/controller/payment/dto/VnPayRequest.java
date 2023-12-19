package com.sudo248.orderservice.controller.payment.dto;

import lombok.Data;

@Data
public class VnPayRequest {
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_Amount;
    private String vnp_OrderInfo;
    private String vnp_ResponseCode;

}
