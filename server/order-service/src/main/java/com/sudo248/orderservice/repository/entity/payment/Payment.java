package com.sudo248.orderservice.repository.entity.payment;

import com.sudo248.orderservice.repository.entity.order.Order;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "order_type")
    private String orderType;

    private Double amount;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "status")
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payment")
    private Order order;

}
