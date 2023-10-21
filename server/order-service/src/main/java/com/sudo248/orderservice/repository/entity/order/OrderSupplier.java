package com.sudo248.orderservice.repository.entity.order;

import com.sudo248.orderservice.controller.order.dto.CartProductDto;
import com.sudo248.orderservice.controller.order.dto.OrderProductInfoDto;
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

    private double promotionValue;

    @Embedded
    private Shipment shipment;

    @Transient
    private List<CartProductDto> cartProducts;
}
