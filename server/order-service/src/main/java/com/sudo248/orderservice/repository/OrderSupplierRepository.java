package com.sudo248.orderservice.repository;

import com.sudo248.orderservice.repository.entity.order.OrderSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSupplierRepository extends JpaRepository<OrderSupplier, String> {
}
