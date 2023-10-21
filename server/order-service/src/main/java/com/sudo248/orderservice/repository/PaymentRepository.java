package com.sudo248.orderservice.repository;

import com.sudo248.orderservice.repository.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
