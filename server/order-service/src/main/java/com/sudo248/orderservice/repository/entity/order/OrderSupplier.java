package com.sudo248.orderservice.repository.entity.order;

import com.sudo248.orderservice.controller.order.dto.OrderCartProductDto;
import com.sudo248.orderservice.controller.order.dto.SupplierInfoDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_supplier")
@NoArgsConstructor
@AllArgsConstructor
public class OrderSupplier {
    @Id
    @Column(name = "order_supplier_id")
    private String orderSupplierId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "order_id")
    private Order order;

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "promotion_value")
    private double promotionValue;

    @Column(name = "total_price")
    private double totalPrice;

    @Embedded
    private Shipment shipment;

    @Transient
    private List<OrderCartProductDto> cartProducts;

    @Transient
    private SupplierInfoDto supplier;
}
