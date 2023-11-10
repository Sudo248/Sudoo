package com.sudo248.orderservice.repository.entity.order;

import com.sudo248.orderservice.controller.order.dto.PromotionDto;
import com.sudo248.orderservice.repository.entity.payment.Payment;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "total_price")
    private Double totalPrice = 0.0;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "promotion_id")
    private String promotionId;

    @Column(name = "total_promotion_price")
    private Double totalPromotionPrice = 0.0;

    @Column(name = "total_shipment_price")
    private Double totalShipmentPrice = 0.0;

    @Column(name = "final_price")
    private Double finalPrice = 0.0;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderSupplier> orderSuppliers;

    public double calculateTotalPromotionPrice(PromotionDto promotionDto, List<PromotionDto> supplierPromotion) {
        if (promotionDto != null) {
            this.totalPromotionPrice = promotionDto.getValue();
        } else {
            this.totalPromotionPrice = 0.0;
        }
        if (supplierPromotion != null && !supplierPromotion.isEmpty()) {
            this.totalPromotionPrice += supplierPromotion.stream().map(PromotionDto::getValue).reduce(0.0, Double::sum);
        }
        return this.totalPromotionPrice;
    }

    public double calculateTotalShipmentPrice() {
        this.totalShipmentPrice = orderSuppliers.stream().map(orderSupplier -> orderSupplier.getShipment().getShipmentPrice()).reduce(0.0, Double::sum);
        return this.totalShipmentPrice;
    }

    public double calculateFinalPrice() {
        this.finalPrice = totalPrice + totalShipmentPrice - totalPromotionPrice;
        return this.finalPrice;
    }
}
