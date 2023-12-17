package com.sudoo.paymentservice.service;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.base.BaseService;
import com.sudoo.paymentservice.controller.dto.PaymentDto;
import com.sudoo.paymentservice.controller.dto.PaymentInfoDto;
import org.springframework.http.ResponseEntity;


public interface PaymentService extends BaseService {
    ResponseEntity<BaseResponse<?>> pay(String userId, long currentTime, PaymentDto paymentDto);

    PaymentInfoDto getPaymentInfo(String paymentId);
}
