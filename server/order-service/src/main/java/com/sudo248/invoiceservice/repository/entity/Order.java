package com.sudo248.invoiceservice.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "total_promotion_price")
    private Double totalPromotionPrice;

    @Column(name = "final_price")
    private Double finalPrice;

    @Embedded
    private Shipment shipment;
}
