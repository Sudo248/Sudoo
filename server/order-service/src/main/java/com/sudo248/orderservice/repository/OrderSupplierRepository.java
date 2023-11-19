package com.sudo248.orderservice.repository;

import com.sudo248.orderservice.repository.entity.order.Order;
import com.sudo248.orderservice.repository.entity.order.OrderStatus;
import com.sudo248.orderservice.repository.entity.order.OrderSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderSupplierRepository extends JpaRepository<OrderSupplier, String> {
    List<OrderSupplier> getAllBySupplierId(String supplierId);

    List<OrderSupplier> getAllBySupplierIdAndStatus(String supplierId, OrderStatus status);

    @Query(
            value = "SELECT * FROM order_supplier WHERE order_supplier.order_id = :orderId AND order_supplier.supplier_id = :supplierId",
            nativeQuery = true
    )
    Optional<OrderSupplier> getByOrderIdAndSupplierId(@Param("orderId") String orderId, @Param("supplierId") String supplierId);
    List<OrderSupplier> getAllBySupplierIdAndCreatedAtBetween(String supplierId, LocalDateTime from, LocalDateTime to);
}
