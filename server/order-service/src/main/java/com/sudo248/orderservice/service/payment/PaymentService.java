package com.sudo248.orderservice.service.payment;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.base.BaseService;
import com.sudo248.orderservice.controller.payment.dto.PaymentDto;
import com.sudo248.orderservice.controller.payment.dto.PaymentInfoDto;
import com.sudo248.orderservice.repository.entity.payment.Payment;
import org.springframework.http.ResponseEntity;


public interface PaymentService extends BaseService {
    ResponseEntity<BaseResponse<?>> pay(String userId, PaymentDto paymentDto);

    PaymentInfoDto getPaymentInfo(String paymentId);

    PaymentInfoDto toPaymentInfoDto(Payment payment);

    Payment getPaymentById(String paymentId);
}
