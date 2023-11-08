package com.sudo248.orderservice.service.order.impl;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.domain.util.Utils;
import com.sudo248.orderservice.controller.order.dto.*;
import com.sudo248.orderservice.controller.order.dto.ghn.*;
import com.sudo248.orderservice.controller.payment.dto.PaymentInfoDto;
import com.sudo248.orderservice.external.GHNService;
import com.sudo248.orderservice.internal.CartService;
import com.sudo248.orderservice.internal.ProductService;
import com.sudo248.orderservice.internal.UserService;
import com.sudo248.orderservice.repository.OrderRepository;
import com.sudo248.orderservice.repository.OrderSupplierRepository;
import com.sudo248.orderservice.repository.entity.order.*;
import com.sudo248.orderservice.repository.entity.payment.Payment;
import com.sudo248.orderservice.service.order.OrderService;
import com.sudo248.orderservice.service.payment.PaymentService;
import com.sudo248.orderservice.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.of;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CartService cartService;

    private final PaymentService paymentService;

    private final OrderSupplierRepository orderSupplierRepository;

    private final GHNService ghnService;

    private final ProductService productService;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserService userService,
            CartService cartService,
            PaymentService paymentService,
            OrderSupplierRepository orderSupplierRepository,
            GHNService ghnService,
            ProductService productService
    ) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.orderSupplierRepository = orderSupplierRepository;
        this.ghnService = ghnService;
        this.productService = productService;
    }

    @Override
    public List<OrderDto> getOrdersByUserId(String userId) throws ApiException {
        return orderRepository.getOrdersByUserId(userId).stream().map(
                (e) -> {
                    try {
                        return toDto(e);
                    } catch (ApiException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        ).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(String orderId) throws ApiException {
        Order order = orderRepository.getReferenceById(orderId);
        return toDto(order);
    }

    @Override
    public OrderDto createOrder(String userId, UpsertOrderDto upsertOrderDto) throws ApiException {
        Order.OrderBuilder builder = Order.builder()
                .orderId(Utils.createIdOrElse(upsertOrderDto.getOrderId()))
                .status(OrderStatus.PREPARE)
                .cartId(upsertOrderDto.getCartId())
                .userId(userId);

        CartDto cart = getProcessingCart(userId);
        UserDto user = getUserById(userId);

        builder.totalPrice(cart.getTotalPrice());
        builder.address(user.getAddress().getFullAddress());

        PromotionDto promotionDto = null;
        if (!StringUtils.isNullOrEmpty(upsertOrderDto.getPromotionId())) {
            promotionDto = getPromotionById(upsertOrderDto.getPromotionId());
            builder.promotionId(promotionDto.getPromotionId());
        }

        Order order = builder.build();
        // He thong chua ho tro promotion cho tung staff
        order.setOrderSuppliers(createOrderSuppliersByCart(order, cart, user));
        order.calculateTotalPromotionPrice(promotionDto, null);
        order.calculateTotalShipmentPrice();
        order.calculateFinalPrice();

        orderRepository.save(order);
//        41a86e86258493c18c9cb057e07b9508

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCartId())
                .payment(null)
                .promotion(promotionDto)
                .user(user)
                .status(order.getStatus())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .totalShipmentPrice(order.getTotalShipmentPrice())
                .totalPromotionPrice(order.getTotalPromotionPrice())
                .finalPrice(order.getFinalPrice())
                .orderSuppliers(order.getOrderSuppliers().stream().map(this::toOrderSupplierDto).collect(Collectors.toList()))
                .build();

    }

    @Override
    public boolean deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
        return true;
    }

    @Override
    public boolean cancelOrderByUser(String orderId) {
        final Order order = orderRepository.getReferenceById(orderId);
        cartService.deleteProcessingCart(order.getUserId());
        orderRepository.deleteById(orderId);
        return false;
    }

    @Override
    public OrderDto toDto(Order order) throws ApiException {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCartId())
                .payment(paymentService.toPaymentInfoDto(order.getPayment()))
                .promotion(getPromotionById(order.getPromotionId()))
                .user(getUserById(order.getUserId()))
                .status(order.getStatus())
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .totalShipmentPrice(order.getTotalShipmentPrice())
                .totalPromotionPrice(order.getTotalPromotionPrice())
                .finalPrice(order.getFinalPrice())
                .orderSuppliers(order.getOrderSuppliers().stream().map(this::toOrderSupplierDto).collect(Collectors.toList()))
                .build();

    }

    @Override
    public OrderSupplierDto toOrderSupplierDto(OrderSupplier orderSupplier) {
        if (orderSupplier.getSupplier() == null) {
            orderSupplier.setSupplier(productService.getSupplierById(orderSupplier.getSupplierId()).getData());
        }
        PromotionDto promotionDto = null;
        if (!StringUtils.isNullOrEmpty(orderSupplier.getPromotionId())) {
            try {
                promotionDto = getPromotionById(orderSupplier.getPromotionId());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return new OrderSupplierDto(
                orderSupplier.getOrderSupplierId(),
                orderSupplier.getSupplier(),
                promotionDto,
                orderSupplier.getTotalPrice(),
                orderSupplier.getShipment(),
                orderSupplier.getCartProducts()
        );
    }

    @Override
    public Map<String, ?> updateOrderByField(String orderId, String field, String id) throws ApiException {
        Order order = orderRepository.getReferenceById(orderId);
        Map<String, Object> result = new HashMap<>();
        result.put(field, id);
        switch (field) {
            case "promotion":
                PromotionDto promotionDto = getPromotionById(id);
                order.setPromotionId(id);
                order.setTotalPromotionPrice(promotionDto.getValue());
                order.calculateFinalPrice();
                orderRepository.save(order);
                result.putAll(getUpdateOrderMap(order));
        }
        return result;
    }

    @Override
    public UpsertOrderPromotionDto updateOrderPromotion(String orderId, UpsertOrderPromotionDto upsertOrderPromotionDto) throws ApiException {
        PromotionDto promotionDto = getPromotionById(upsertOrderPromotionDto.getPromotionId());
        Order order = orderRepository.getReferenceById(orderId);
        if (StringUtils.isNullOrEmpty(upsertOrderPromotionDto.getOrderSupplierId())) {
            order.setPromotionId(upsertOrderPromotionDto.getPromotionId());
        } else {
            // update promotion for order supplier
        }
        order.calculateTotalPromotionPrice(promotionDto, null);
        order.calculateFinalPrice();
        orderRepository.save(order);
        return new UpsertOrderPromotionDto(
                upsertOrderPromotionDto.getPromotionId(),
                upsertOrderPromotionDto.getOrderSupplierId(),
                order.getTotalPrice(),
                order.getTotalPromotionPrice(),
                order.getFinalPrice()
        );

    }

    private Map<String, Object> getUpdateOrderMap(Order order) {
        return Map.of(
                "totalPrice", order.getTotalPrice(),
                "totalPromotionPrice", order.getTotalPromotionPrice(),
                "totalShipmentPrice", order.getTotalShipmentPrice(),
                "finalPrice", order.getFinalPrice()
        );
    }

    @Override
    public void updateOrderPayment(String orderId, String paymentId) {
        Order order = orderRepository.getReferenceById(orderId);
        Payment payment = paymentService.getPaymentById(paymentId);
        order.setPayment(payment);
        orderRepository.save(order);
    }

    //Request Detail
    private PromotionDto getPromotionById(String promotionId) throws ApiException {
        if (promotionId == null) return new PromotionDto();
        var response = productService.getPromotionById(promotionId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found promotion " + promotionId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private UserDto getUserById(String userId) {
        ResponseEntity<BaseResponse<UserDto>> response = userService.getUser(userId);
        if (response.getStatusCodeValue() == 200) {
            return Objects.requireNonNull(response.getBody()).getData();
        }
        return new UserDto();
    }

    private CartDto getProcessingCart(String userId) throws ApiException {
        var response = cartService.getProcessingCart(userId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found processing cart for user " + userId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private PaymentInfoDto getPaymentById(String paymentId) {
        if (paymentId == null) return null;
        return paymentService.getPaymentInfo(paymentId);
    }

    private Shipment calculateShipment(int shopId, CalculateFeeRequest calculateFeeRequest, CalculateExpectedTimeRequest calculateExpectedTimeRequest) throws ApiException {
        GHNResponse<CalculateFeeDto> responseFee = ghnService.calculateFee(shopId, calculateFeeRequest);
        if (responseFee.getCode() != 200) {
            throw new ApiException(HttpStatus.BAD_REQUEST, responseFee.getMessage());
        }
        GHNResponse<CalculateExpectedTimeDto> responseExpectedTime = ghnService.calculateExpectedTime(shopId, calculateExpectedTimeRequest);
        if (responseExpectedTime.getCode() != 200) {
            throw new ApiException(HttpStatus.BAD_REQUEST, responseFee.getMessage());
        }
        return new Shipment(
                ShipmentType.values()[calculateFeeRequest.getServiceTypeId()],
                responseExpectedTime.getData().getLeadTime(),
                null,
                responseFee.getData().getTotal()
        );
    }

    private List<OrderSupplier> createOrderSuppliersByCart(Order order, CartDto cart, UserDto user) throws ApiException {
        final Map<String, List<OrderCartProductDto>> groupBySupplier = cart.getCartProducts().stream().collect(Collectors.groupingBy(orderCartProductDto ->
                orderCartProductDto.getProduct().getSupplierId()
        ));
        final ArrayList<OrderSupplier> listOrderSupplier = new ArrayList<>();

        // should use stream parallel
        for (Map.Entry<String, List<OrderCartProductDto>> entry : groupBySupplier.entrySet()) {
            String supplierId = entry.getKey();
            List<OrderCartProductDto> cartProducts = entry.getValue();

            final SupplierInfoDto supplier = productService.getSupplierById(supplierId).getData();

            final var orderSupplierBuilder = OrderSupplier.builder()
                    .orderSupplierId(Utils.createId())
                    .order(order)
                    .supplierId(supplierId)
                    .supplier(supplier)
                    .promotionId(null)
                    .cartProducts(cartProducts);

            // Length is same for an product
            int totalLength = cartProducts.get(0).getProduct().getLength();
            int totalWeight = 0, totalWidth = 0, totalHeight = 0;
            double totalPrice = 0.0;
            for (OrderCartProductDto cartProduct : cartProducts) {
                totalPrice += cartProduct.getTotalPrice();
                totalWeight += cartProduct.getProduct().getWeight() * cartProduct.getQuantity();
                totalWidth += cartProduct.getProduct().getWidth() * cartProduct.getQuantity();
                totalHeight += cartProduct.getProduct().getHeight() * cartProduct.getQuantity();
            }
            final CalculateFeeRequest calculateFeeRequest = CalculateFeeRequest.builder()
                    .serviceTypeId(GHNService.DEFAULT_SHIPMENT_TYPE.ordinal())
                    .fromDistrictId(supplier.getAddress().getDistrictID())
                    .fromWardCode(supplier.getAddress().getWardCode())
                    .toDistrictId(user.getAddress().getDistrictID())
                    .toWardCode(user.getAddress().getWardCode())
                    .weight(totalWeight)
                    .length(totalLength)
                    .width(totalWidth)
                    .height(totalHeight)
                    .build();

            final CalculateExpectedTimeRequest calculateExpectedTimeRequest = CalculateExpectedTimeRequest.builder()
                    .serviceId(GHNService.DEFAULT_SHIPMENT_TYPE.ordinal())
                    .fromDistrictId(supplier.getAddress().getDistrictID())
                    .fromWardCode(supplier.getAddress().getWardCode())
                    .toDistrictId(user.getAddress().getDistrictID())
                    .toWardCode(user.getAddress().getWardCode())
                    .build();

            orderSupplierBuilder
                    .totalPrice(totalPrice)
                    .shipment(calculateShipment(supplier.getGhnShopId(), calculateFeeRequest, calculateExpectedTimeRequest));

            listOrderSupplier.add(orderSupplierBuilder.build());
        }
        return listOrderSupplier;
    }
}
