package com.sudoo.paymentservice.repository;

import com.sudoo.paymentservice.repository.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
