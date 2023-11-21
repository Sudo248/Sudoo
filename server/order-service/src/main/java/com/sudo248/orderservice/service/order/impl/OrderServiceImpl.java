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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        List<Order> orders = orderRepository.getOrdersByUserId(userId);
        return orders.stream().filter((e) -> e.getPayment() != null).map(
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
        } else {
            builder.totalPromotionPrice(0.0);
        }

        Order order = builder.build();
        order.setOrderSuppliers(createOrderSuppliersByCart(order, cart, user));
        // He thong chua ho tro promotion cho tung staff
        order.calculateTotalPromotionPrice(promotionDto, null);
        order.calculateTotalShipmentPrice();
        order.calculateFinalPrice();

        orderRepository.save(order);

        final LocalDateTime orderCreatedAt;
        if (order.getOrderSuppliers().isEmpty()) {
            orderCreatedAt = LocalDateTime.now();
        } else {
            orderCreatedAt = order.getOrderSuppliers().get(0).getCreatedAt();
        }

        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCartId())
                .payment(null)
                .promotion(promotionDto)
                .user(user)
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .totalShipmentPrice(order.getTotalShipmentPrice())
                .totalPromotionPrice(order.getTotalPromotionPrice())
                .finalPrice(order.getFinalPrice())
                .createdAt(orderCreatedAt)
                .orderSuppliers(order.getOrderSuppliers().stream().map(orderSupplier -> toOrderSupplierDto(orderSupplier)).collect(Collectors.toList()))
                .build();

    }

    @Override
    public boolean deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
        return true;
    }

    @Override
    public boolean cancelOrderByUser(String orderId) throws ApiException {
        final Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Not found order " + orderId);
        cartService.deleteProcessingCart(order.get().getUserId());
        orderRepository.deleteById(orderId);
        return false;
    }

    @Override
    public OrderDto toDto(Order order) throws ApiException {
        final LocalDateTime orderCreatedAt;
        if (order.getOrderSuppliers().isEmpty()) {
            orderCreatedAt = LocalDateTime.now();
        } else {
            orderCreatedAt = order.getOrderSuppliers().get(0).getCreatedAt();
        }
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCartId())
                .payment(paymentService.toPaymentInfoDto(order.getPayment()))
                .promotion(getPromotionById(order.getPromotionId()))
                .user(getUserById(order.getUserId()))
                .address(order.getAddress())
                .totalPrice(order.getTotalPrice())
                .totalShipmentPrice(order.getTotalShipmentPrice())
                .totalPromotionPrice(order.getTotalPromotionPrice())
                .finalPrice(order.getFinalPrice())
                .orderSuppliers(order.getOrderSuppliers().stream().map(this::toOrderSupplierDto).collect(Collectors.toList()))
                .createdAt(orderCreatedAt)
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
                orderSupplier.getCreatedAt().plusSeconds(orderSupplier.getShipment().getDeliveryTime() / 1000),
                orderSupplier.getStatus(),
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
        Optional<Order> _order = orderRepository.findById(orderId);
        if (_order.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Not found order" + orderId);
        final Order order = _order.get();
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

    @Override
    public OrderDto getOrderByOrderSupplierIdAndSupplierFromUserId(String orderSupplierId, String userId) throws ApiException {
        final SupplierInfoDto supplier = productService.getSupplierByUserId(userId).getData();
        Optional<OrderSupplier> orderSupplier = orderSupplierRepository.findById(orderSupplierId);
        if (orderSupplier.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found order supplier " + orderSupplierId);
        return getOrderOfSupplier(orderSupplier.get(), supplier);
    }

    @Override
    public List<OrderSupplierInfoDto> getListOrderSupplierInfoFromUserId(String userId, OrderStatus status) throws ApiException {
        final SupplierInfoDto supplier = productService.getSupplierByUserId(userId).getData();
        List<OrderSupplier> orderSuppliers;
        if (status == null) {
            orderSuppliers = orderSupplierRepository.getAllBySupplierId(supplier.getSupplierId());
        } else {
            orderSuppliers = orderSupplierRepository.getAllBySupplierIdAndStatus(supplier.getSupplierId(), status);
        }

        return orderSuppliers.stream().map((orderSupplier -> {
            Order order = orderSupplier.getOrder();
            String paymentType = null;
            LocalDateTime paymentDateTime = null;
            if (order.getPayment() != null) {
                paymentType = order.getPayment().getPaymentType();
                paymentDateTime = order.getPayment().getPaymentDateTime();
            }
            final UserDto userDto = getUserById(order.getUserId());
            return OrderSupplierInfoDto.builder()
                    .orderSupplierId(orderSupplier.getOrderSupplierId())
                    .supplierId(supplier.getSupplierId())
                    .supplierName(supplier.getName())
                    .userFullName(userDto.getFullName())
                    .userPhoneNumber(userDto.getEmailOrPhoneNumber())
                    .paymentType(paymentType)
                    .paymentDateTime(paymentDateTime)
                    .status(orderSupplier.getStatus())
                    .address(order.getAddress())
                    .expectedReceiveDateTime(orderSupplier.getCreatedAt().plusSeconds(orderSupplier.getShipment().getDeliveryTime() / 1000))
                    .totalPrice(orderSupplier.getTotalPrice())
                    .createdAt(orderSupplier.getCreatedAt())
                    .build();
        })).collect(Collectors.toList());
    }

    @Override
    public List<OrderUserInfoDto> getListOrderUserInfoByUserId(String userId, OrderStatus status) throws ApiException {
        List<Order> orders = orderRepository.getOrdersByUserId(userId);
        List<OrderUserInfoDto> orderUserInfoDtos = new ArrayList<>();
        for (Order order : orders) {
            final CartDto cart = getOrderCartById(order.getCartId(), "");
            final Map<String, List<OrderCartProductDto>> groupBySupplier = cart.getCartProducts().stream().collect(Collectors.groupingBy(orderCartProductDto ->
                    orderCartProductDto.getProduct().getSupplierId()
            ));
            final List<OrderSupplier> orderSuppliers;

            if (status != null) {
                orderSuppliers = order.getOrderSuppliers().stream().filter((e) -> e.getStatus() == status).collect(Collectors.toList());
            } else {
                orderSuppliers = order.getOrderSuppliers();
            }
            final List<OrderSupplierUserInfoDto> orderSupplierUserInfoDtos = orderSuppliers.stream().map((orderSupplier) -> {
                        final SupplierInfoDto supplier = productService.getSupplierById(orderSupplier.getSupplierId()).getData();
                        return OrderSupplierUserInfoDto.builder()
                                .orderSupplierId(orderSupplier.getOrderSupplierId())
                                .supplierId(supplier.getSupplierId())
                                .supplierName(supplier.getName())
                                .supplierAvatar(supplier.getAvatar())
                                .supplierBrand(supplier.getBrand())
                                .supplierContactUrl(supplier.getContactUrl())
                                .status(orderSupplier.getStatus())
                                .expectedReceiveDateTime(orderSupplier.getCreatedAt().plusSeconds(orderSupplier.getShipment().getDeliveryTime() / 1000))
                                .totalPrice(orderSupplier.getTotalPrice())
                                .orderCartProducts(groupBySupplier.get(orderSupplier.getSupplierId()))
                                .build();
                    }
            ).collect(Collectors.toList());
            LocalDateTime orderCreatedAt = LocalDateTime.now();
            if (!orderSuppliers.isEmpty()) {
                orderCreatedAt = orderSuppliers.get(0).getCreatedAt();
            }
            orderUserInfoDtos.add(
                    OrderUserInfoDto.builder()
                            .orderId(order.getOrderId())
                            .finalPrice(order.getFinalPrice())
                            .createdAt(orderCreatedAt)
                            .orderSuppliers(orderSupplierUserInfoDtos)
                            .build()
            );
        }
        return orderUserInfoDtos;
    }

    @Override
    public Map<String, Object> patchOrderSupplier(String orderSupplierId, PatchOrderSupplierDto patchOrderSupplierDto) throws ApiException {
        Optional<OrderSupplier> orderSupplierResult = orderSupplierRepository.findById(orderSupplierId);
        if (orderSupplierResult.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found order supplier " + orderSupplierId);
        OrderSupplier orderSupplier = orderSupplierResult.get();
        Map<String, Object> response = new HashMap<>();

        if (patchOrderSupplierDto.getStatus() != null) {
            orderSupplier.setStatus(patchOrderSupplierDto.getStatus());
            response.put("status", patchOrderSupplierDto.getStatus());
        }

        if (patchOrderSupplierDto.getReceivedDateTime() != null) {
            orderSupplier.setStatus(OrderStatus.RECEIVED);
            orderSupplier.getShipment().setReceivedDateTime(patchOrderSupplierDto.getReceivedDateTime());
            response.put("receivedDateTime", patchOrderSupplierDto.getReceivedDateTime());
        }

        orderSupplierRepository.save(orderSupplier);
        return response;
    }

    @Override
    public RevenueStatisticData statisticRevenue(String userId, StatisticRevenueCondition condition, LocalDate from, LocalDate to) {
        final SupplierInfoDto supplier = productService.getSupplierByUserId(userId).getData();
        final List<OrderSupplier> orderSuppliers = orderSupplierRepository.getAllBySupplierIdAndCreatedAtBetween(supplier.getSupplierId(), from.atStartOfDay(), to.atTime(LocalTime.MAX));
//        orderSuppliers.sort(Comparator.comparing(OrderSupplier::getCreatedAt).reversed());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(condition.format);
        Map<String, Double> response = new HashMap<>();
        from.datesUntil(to).collect(Collectors.toList()).forEach((e) -> response.put(formatter.format(e), 0.0));
        double total = 0.0;
        for (OrderSupplier orderSupplier : orderSuppliers) {
            final String key = formatter.format(orderSupplier.getCreatedAt());
            final double currentValue = response.get(key);
            response.put(key, currentValue + orderSupplier.getTotalPrice());
            total += orderSupplier.getTotalPrice();
        }

        return RevenueStatisticData.builder()
                .data(response)
                .total(total)
                .build();
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

    private CartDto getOrderCartById(String cartId, String supplierId) throws ApiException {
        var response = cartService.getCartById(cartId, true, supplierId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found processing cart " + cartId);
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
        final LocalDateTime createdAt = LocalDateTime.now();
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
                    .status(OrderStatus.PREPARE)
                    .cartProducts(cartProducts)
                    .createdAt(createdAt);

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

    OrderDto getOrderOfSupplier(OrderSupplier orderSupplier, SupplierInfoDto supplierInfoDto) throws ApiException {
        orderSupplier.setSupplier(supplierInfoDto);
        CartDto cart = getOrderCartById(orderSupplier.getOrder().getCartId(), supplierInfoDto.getSupplierId());
        orderSupplier.setCartProducts(cart.getCartProducts());
        Order order = orderSupplier.getOrder();
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .cartId(order.getCartId())
                .payment(paymentService.toPaymentInfoDto(order.getPayment()))
                .promotion(getPromotionById(order.getPromotionId()))
                .user(getUserById(order.getUserId()))
                .address(order.getAddress())
                .totalPrice(orderSupplier.getTotalPrice())
                .totalShipmentPrice(orderSupplier.getShipment().getShipmentPrice())
                .totalPromotionPrice(0.0)
                .finalPrice(orderSupplier.getTotalPrice())
                .createdAt(orderSupplier.getCreatedAt())
                .orderSuppliers(List.of(toOrderSupplierDto(orderSupplier)))
                .build();
    }
}
