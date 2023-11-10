package com.sudo248.orderservice.repository;

import com.sudo248.orderservice.repository.entity.order.Order;
import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> getOrdersByUserId(String userId);

    List<Order> getOrdersByUserIdAndStatus(String userId, OrderStatus status);
}
