package com.sudo248.invoiceservice.internal;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.invoiceservice.controller.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PAYMENT-SERVICE")
@Service
public interface PaymentService {

    @GetMapping("/api/v1/payment/payment-info/{paymentId}")
    ResponseEntity<BaseResponse<PaymentDto>> getPaymentById(@PathVariable("paymentId") String paymentId);

}
