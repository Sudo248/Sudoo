package com.sudo248.invoiceservice.service.serviceImpl;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.domain.util.Utils;
import com.sudo248.invoiceservice.controller.dto.*;
import com.sudo248.invoiceservice.internal.CartService;
import com.sudo248.invoiceservice.internal.PaymentService;
import com.sudo248.invoiceservice.internal.PromotionService;
import com.sudo248.invoiceservice.internal.UserService;
import com.sudo248.invoiceservice.repository.OrderRepository;
import com.sudo248.invoiceservice.repository.entity.Order;
import com.sudo248.invoiceservice.repository.entity.OrderStatus;
import com.sudo248.invoiceservice.repository.entity.Shipment;
import com.sudo248.invoiceservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CartService cartService;

    private final PaymentService paymentService;

    private final PromotionService promotionService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService, PaymentService paymentService, PromotionService promotionService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.promotionService = promotionService;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) throws ApiException {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getUserId().equals(userId))
                orderDtoList.add(toDto(order));
        }
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderById(String invoiceId) throws ApiException {
        Order order = orderRepository.getReferenceById(invoiceId);
        return toDto(order);
    }

    @Override
    public UpsertOrderDto upsertOrder(String userId, UpsertOrderDto upsertOrderDto) throws ApiException {
        Order order = new Order();

        order.setOrderId(Utils.createIdOrElse(upsertOrderDto.getOrderId()));
        order.setPaymentId(upsertOrderDto.getPaymentId());
        order.setCartId(upsertOrderDto.getCartId());
        order.setUserId(userId);
        order.setPromotionId(upsertOrderDto.getPromotionId());
        order.setStatus(upsertOrderDto.getStatus());
        order.setStatus(OrderStatus.PREPARE);

        CartDto cartDto = getCartById(userId, upsertOrderDto.getCartId(), true);
        log.info("cartDto: " + cartDto);
        order.setShipment(calculateShipment(cartDto));

        order.setTotalPrice(cartDto.getTotalPrice() + order.getShipment().getPriceShipment());
        order.setTotalPromotionPrice(0.0);
        order.setFinalPrice(order.getTotalPrice() - order.getTotalPromotionPrice());
        orderRepository.save(order);

        upsertOrderDto.setOrderId(order.getOrderId());
        upsertOrderDto.setTotalPromotionPrice(order.getTotalPromotionPrice());
        upsertOrderDto.setTotalPrice(order.getTotalPrice());
        upsertOrderDto.setFinalPrice(order.getFinalPrice());
        return upsertOrderDto;
    }

    @Override
    public boolean deleteInvoice(String invoiceId) {
        orderRepository.findById(invoiceId);
        orderRepository.deleteById(invoiceId);
        return true;
    }

    @Override
    public OrderDto toDto(Order order) throws ApiException {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCart(getCartById(order.getUserId(), order.getCartId(), false));
        orderDto.setPayment(getPaymentById(order.getPaymentId()));
        orderDto.setPromotion(getPromotionById(order.getPromotionId()));
        orderDto.setUser(getUserById(order.getUserId()));
        orderDto.setStatus(order.getStatus());
        orderDto.setShipment(order.getShipment());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setTotalPromotionPrice(orderDto.getPromotion().getValue());
        orderDto.setFinalPrice(order.getTotalPrice() - orderDto.getTotalPromotionPrice());
        return orderDto;
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderId(Utils.createIdOrElse(orderDto.getOrderId()));
        order.setPaymentId(orderDto.getPayment().getPaymentId());
        order.setCartId(orderDto.getCart().getCartId());
        order.setUserId(orderDto.getUser().getUserId());
        order.setPromotionId(orderDto.getPromotion().getPromotionId());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setStatus(orderDto.getStatus());
        order.setTotalPromotionPrice(orderDto.getTotalPromotionPrice());
        order.setFinalPrice(orderDto.getFinalPrice());
        return order;
    }

    @Override
    public OrderDto updateOrderByField(String invoiceId, String field, String id) throws ApiException {
        Order order = orderRepository.getReferenceById(invoiceId);
        switch (field) {
            case "promotion":
                PromotionDto promotionDto = getPromotionById(id);
                order.setPromotionId(id);
                order.setTotalPromotionPrice(promotionDto.getValue());
                order.setFinalPrice(order.getTotalPrice() - order.getTotalPromotionPrice());
                orderRepository.save(order);
                return toDto(order);
        }
        return null;
    }

    @Override
    public void updateOrderPayment(String invoiceId, String paymentId) {
        Order order = orderRepository.getReferenceById(invoiceId);
        order.setPaymentId(paymentId);
        orderRepository.save(order);
    }

    //Request Detail
    private PromotionDto getPromotionById(String promotionId) throws ApiException {
        if (promotionId == null) return new PromotionDto();
        var response = promotionService.getPromotionById(promotionId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found promotion " + promotionId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private UserDto getUserById(String userId) {
        ResponseEntity<BaseResponse<UserDto>> response = userService.getUserInfo(userId);
        if (response.getStatusCodeValue() == 200) {
            return Objects.requireNonNull(response.getBody()).getData();
        }
        return new UserDto();
    }

    private CartDto getCartById(String userId, String cartId, boolean hasRoute) throws ApiException {
        var response = cartService.getCartById(userId, cartId, hasRoute);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found cart " + cartId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private PaymentDto getPaymentById(String paymentId) throws ApiException {
        if (paymentId == null) return null;
        var response = paymentService.getPaymentById(paymentId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found payment " + paymentId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private Shipment calculateShipment(CartDto cartDto) {
        List<RouteDto> routes = cartDto.getCartSupplierProducts().stream().map(cpd -> cpd.getSupplierProduct().getRoute()).collect(Collectors.toList());
        RouteDto maxRoute = routes.stream().max(Comparator.comparingDouble(r -> r.getDistance().getValue())).get();
        return new Shipment(
                "Nhanh",
                (int) maxRoute.getDuration().getValue(),
                maxRoute.getDuration().getUnit(),
                calculateShipmentPrice(maxRoute.getDistance())
        );
    }

    private double calculateShipmentPrice(ValueDto distance) {
        if (distance.getUnit().equals("km")) {
            return ((int) distance.getValue()) * 5000;
        } else {
            return ((int) distance.getValue()) * 50;
        }
    }
// https://google/ogp?url=https%3A%2F%2Fd1hjkbq40fs2x4.cloudfront.net%2F2016-01-31%2Ffiles%2F1045.jpg&title=test&description=123&image=https%3A%2F%2Fencrypted-tbn0.gstatic.com%2Fimages%3Fq%3Dtbn%3AANd9GcSsGIp1RbFF_EUgR0Y-CUQYgIZ76BO27pSpcTL9VfHn%26s
}
