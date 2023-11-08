package com.sudo248.orderservice.service.payment.impl;

import com.sudo248.domain.base.BaseResponse;
import com.sudo248.domain.exception.ApiException;
import com.sudo248.domain.util.Utils;
import com.sudo248.orderservice.config.VnPayConfig;
import com.sudo248.orderservice.controller.order.dto.*;
import com.sudo248.orderservice.controller.payment.dto.PaymentDto;
import com.sudo248.orderservice.controller.payment.dto.PaymentInfoDto;
import com.sudo248.orderservice.controller.payment.dto.VnPayResponse;
import com.sudo248.orderservice.internal.CartService;
import com.sudo248.orderservice.internal.NotificationService;
import com.sudo248.orderservice.internal.ProductService;
import com.sudo248.orderservice.repository.OrderRepository;
import com.sudo248.orderservice.repository.PaymentRepository;
import com.sudo248.orderservice.repository.entity.order.Order;
import com.sudo248.orderservice.repository.entity.payment.Notification;
import com.sudo248.orderservice.repository.entity.payment.Payment;
import com.sudo248.orderservice.repository.entity.payment.PaymentStatus;
import com.sudo248.orderservice.service.payment.VnpayService;
import com.sudo248.orderservice.service.payment.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class VnPayServiceImpl implements PaymentService, VnpayService {
    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final NotificationService notificationService;

    private final ProductService productService;

    private final Locale locale = new Locale("vi", "VN");

    private final Executor executor = Executors.newSingleThreadExecutor();

    public VnPayServiceImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            CartService cartService,
            NotificationService notificationService,
            ProductService productService) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.notificationService = notificationService;
        this.productService = productService;
    }

    public ResponseEntity<BaseResponse<?>> pay(String userId, PaymentDto paymentDto) {
        return handleException(() -> {
            if (paymentDto.getOrderId() == null) throw new ApiException(HttpStatus.BAD_REQUEST, "Require order id");
            Optional<Order> order = orderRepository.findById(paymentDto.getOrderId());
            if (order.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Not found order " + paymentDto.getOrderId());
            updateOrderStatus(order.get());
            Payment payment = toEntity(paymentDto, order.get());
            order.get().setPayment(payment);
            paymentRepository.save(payment);
            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", VnPayConfig.vnp_Version);
            vnp_Params.put("vnp_Command", VnPayConfig.vnp_Command);
            vnp_Params.put("vnp_TmnCode", VnPayConfig.vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(VnPayConfig.getVnPayAmount(payment.getAmount())));
            vnp_Params.put("vnp_CurrCode", VnPayConfig.vnp_CurrCode);

            if (payment.getBankCode() != null && !payment.getBankCode().isEmpty()) {
                vnp_Params.put("vnp_BankCode", payment.getBankCode());
            }

            vnp_Params.put("vnp_TxnRef", payment.getPaymentId());
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + payment.getPaymentId());
            vnp_Params.put("vnp_OrderType", payment.getOrderType());
            vnp_Params.put("vnp_Locale", VnPayConfig.vnp_Locale);
            vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", paymentDto.getIpAddress());

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone(paymentDto.getTimeZoneId()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());

            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();

            while (itr.hasNext()) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }

            String queryUrl = query.toString();
            String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnPayConfig.vnp_Url + "?" + queryUrl;
            return BaseResponse.ok(toDto(payment, paymentUrl, paymentDto.getTimeZoneId()));
        });
    }

    private void updateOrderStatus(Order order) throws ApiException {
        updateAmountProduct(order.getUserId(), false);
        if (order.getPromotionId() != null && !order.getPromotionId().isBlank()) {
            updateAmountPromotion(order.getPromotionId());
        }
        checkoutProcessingCart(order.getUserId());
    }

    private void checkoutProcessingCart(String userId) {
        cartService.checkoutProcessingCart(userId);
    }

    private void updateAmountProduct(String userId, Boolean isRestore) throws ApiException {
        CartDto cart = getProcessingCart(userId);
        for (OrderCartProductDto orderCartProductDto : cart.getCartProducts()) {
            updateAmountProduct(orderCartProductDto, isRestore);
        }
    }

    // isRestore == true => hoan lai so luong san pham
    private void updateAmountProduct(OrderCartProductDto orderCartProductDto, Boolean isRestore) throws ApiException {
        final PutAmountProductDto putAmountProductDto = new PutAmountProductDto(
                orderCartProductDto.getProduct().getProductId(),
                isRestore ? orderCartProductDto.getQuantity() : -orderCartProductDto.getQuantity()
        );
        ResponseEntity<BaseResponse<?>> response = productService.putProductAmount(putAmountProductDto);
        if (!response.hasBody()) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Some thing went wrong");
        }
        if (!Objects.requireNonNull(response.getBody()).isSuccess()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, response.getBody().getMessage());
        }
    }

    private void updateAmountPromotion(String promotionId) throws ApiException {
        final PutAmountPromotionDto promotionDto = new PutAmountPromotionDto(
                promotionId,
                1
        );
        ResponseEntity<BaseResponse<?>> response = productService.putPromotionAmount(promotionDto);
        if (!response.hasBody()) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Some thing went wrong");
        }
        if (!Objects.requireNonNull(response.getBody()).isSuccess()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, response.getBody().getMessage());
        }
    }

    private CartDto getProcessingCart(String userId) throws ApiException {
        var response = cartService.getProcessingCart(userId);
        if (response.getStatusCode() != HttpStatus.OK || !response.hasBody())
            throw new ApiException(HttpStatus.NOT_FOUND, "Not found processing cart for user" + userId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    @Override
    public PaymentInfoDto getPaymentInfo(String paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        return new PaymentInfoDto(
                paymentId,
                payment.getAmount(),
                payment.getPaymentType()
        );
    }

    @Override
    public PaymentInfoDto toPaymentInfoDto(Payment payment) {
        return new PaymentInfoDto(
                payment.getPaymentId(),
                payment.getAmount(),
                payment.getPaymentType()
        );
    }

    @Override
    public Payment getPaymentById(String paymentId) {
        return paymentRepository.getReferenceById(paymentId);
    }

    private Payment toEntity(PaymentDto paymentDto, Order order) {
        return new Payment(
                Utils.createIdOrElse(paymentDto.getPaymentId()),
                paymentDto.getOrderType(),
                paymentDto.getAmount(),
                paymentDto.getBankCode(),
                paymentDto.getPaymentType(),
                paymentDto.getIpAddress(),
                PaymentStatus.PENDING,
                order
        );
    }

    private PaymentDto toDto(Payment payment, String paymentUrl, String timeZoneId) {
        return new PaymentDto(
                payment.getPaymentId(),
                payment.getOrder().getOrderId(),
                payment.getOrderType(),
                payment.getBankCode(),
                payment.getAmount(),
                payment.getPaymentType(),
                payment.getIpAddress(),
                timeZoneId,
                paymentUrl,
                payment.getStatus()
        );
    }

    @Override
    public String returnVnPay(
            String vnp_TmnCode,
            long vnp_Amount,
            String vnp_BankCode,
            String vnp_BankTranNo,
            String vnp_CardType,
            long vnp_PayDate,
            String vnp_OrderInfo,
            long vnp_TransactionNo,
            String vnp_ResponseCode,
            String vnp_TransactionStatus,
            String vnp_TxnRef,
            String vnp_SecureHashType,
            String vnp_SecureHash
    ) {
        Map<String, String> fields = new HashMap<>();
        fields.put("vnp_TmnCode", vnp_TmnCode);
        fields.put("vnp_Amount", String.valueOf(vnp_Amount));
        fields.put("vnp_BankCode", vnp_BankCode);
        fields.put("vnp_BankTranNo", vnp_BankTranNo);
        fields.put("vnp_CardType", vnp_CardType);
        fields.put("vnp_PayDate", String.valueOf(vnp_PayDate));
        fields.put("vnp_OrderInfo", vnp_OrderInfo);
        fields.put("vnp_TransactionNo", String.valueOf(vnp_TransactionNo));
        fields.put("vnp_ResponseCode", vnp_ResponseCode);
        fields.put("vnp_TransactionStatus", vnp_TransactionStatus);
        fields.put("vnp_TxnRef", vnp_TxnRef);

//        String signValue = VnPayConfig.hashAllFields(fields);

//        if (signValue.equals(vnp_SecureHash)) {
        if ("00".equals(vnp_TransactionStatus)) {
            return "payment_successful";
        } else {
            return "payment_fail";
        }
//        } else {
//            return "payment_fail";
//        }
    }

    // vnpay call to update merchant payment
    // This API will be call to verify update payment in merchant website after that call return url
    @Override
    public VnPayResponse ipnVnpay(
            String vnp_TmnCode,
            long vnp_Amount,
            String vnp_BankCode,
            String vnp_BankTranNo,
            String vnp_CardType,
            long vnp_PayDate,
            String vnp_OrderInfo,
            long vnp_TransactionNo,
            String vnp_ResponseCode,
            String vnp_TransactionStatus,
            String vnp_TxnRef,
            String vnp_SecureHashType,
            String vnp_SecureHash
    ) {
        Map<String, String> fields = new HashMap<>();
        fields.put("vnp_TmnCode", vnp_TmnCode);
        fields.put("vnp_Amount", String.valueOf(vnp_Amount));
        fields.put("vnp_BankCode", vnp_BankCode);
        fields.put("vnp_BankTranNo", vnp_BankTranNo);
        fields.put("vnp_CardType", vnp_CardType);
        fields.put("vnp_PayDate", String.valueOf(vnp_PayDate));
        fields.put("vnp_OrderInfo", vnp_OrderInfo);
        fields.put("vnp_TransactionNo", String.valueOf(vnp_TransactionNo));
        fields.put("vnp_ResponseCode", vnp_ResponseCode);
        fields.put("vnp_TransactionStatus", vnp_TransactionStatus);
        fields.put("vnp_TxnRef", vnp_TxnRef);

//        String signValue = VnPayConfig.hashAllFields(fields);

        VnPayResponse response;

//        if (signValue.equals(vnp_SecureHash)) {
        response = checkAndUpdatePayment(
                vnp_TxnRef,
                vnp_Amount,
                vnp_BankCode,
                vnp_TransactionStatus,
                vnp_ResponseCode
        );
//        } else {
//            response = new VnPayResponse(
//                    "99",
//                    "Unknown error"
//            );
//        }
        return response;
    }

    private VnPayResponse checkAndUpdatePayment(
            String paymentId,
            long amount,
            String bankCode,
            String paymentStatus,
            String responseCode
    ) {

        if (paymentRepository.existsById(paymentId)) {
            Payment payment = paymentRepository.getReferenceById(paymentId);

            if ((long) (payment.getAmount() * 100) == amount) {

                if ("00".equals(paymentStatus) || payment.getStatus() == PaymentStatus.PENDING) {

                    Notification notification;

                    if ("00".equals(responseCode)) {
                        payment.setStatus(PaymentStatus.SUCCESS);
                        upsertUserProduct(payment.getOrder().getUserId());
                        notification = new Notification(
                                null,
                                "Thanh toán thành công",
                                "Thanh toán thành công: " + NumberFormat.getCurrencyInstance(locale).format(payment.getAmount())
                        );
                    } else {
                        payment.setStatus(PaymentStatus.FAILURE);
                        notification = new Notification(
                                null,
                                "Thanh toán thất bại",
                                "Có lỗi xảy ra trong quá trình thanh toán. Hãy kiểm tra lại"
                        );
                        // if thanh toan that bai => tra lai so luong cho san pham
                        try {
                            updateAmountProduct(payment.getOrder().getUserId(), true);
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                    }
                    notificationService.sendNotificationPaymentStatus(payment.getOrder().getUserId(), notification);
                    if (payment.getBankCode() == null || payment.getBankCode().isEmpty()) {
                        payment.setBankCode(bankCode);
                    }
                    paymentRepository.save(payment);
                    return new VnPayResponse(
                            "00",
                            "Confirm Success"
                    );

                } else {
                    return new VnPayResponse(
                            "02",
                            "Payment already confirmed"
                    );
                }

            } else {
                return new VnPayResponse(
                        "04",
                        "Invalid Amount"
                );
            }

        } else {
            return new VnPayResponse(
                    "01",
                    "Payment not Found"
            );
        }
    }

    private void upsertUserProduct(String userId) {
        CompletableFuture.runAsync(() -> {
            try {
                CartDto cart = getProcessingCart(userId);
                CompletableFuture.allOf(cart.getCartProducts().stream().map((e) ->
                        CompletableFuture.runAsync(() ->
                                productService.upsertUserProduct(
                                        userId,
                                        new UpsertUserProductDto(e.getProduct().getProductId())
                                )
                        )
                ).toArray(CompletableFuture[]::new));
            } catch (ApiException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, executor);
    }
}

/*import java.util.Arrays;
        import java.util.List;
        import java.util.concurrent.CompletableFuture;
        import java.util.concurrent.ExecutionException;
        import java.util.concurrent.TimeUnit;
        import java.util.stream.Collectors;

public class CompletableFuture9 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // A list of 100 web page links
        List<String> webPageLinks = Arrays.asList( //
                "https://www.google.com.vn/", "https://vnexpress.net/", "http://gpcoder.com/");

        // Download contents of all the web pages asynchronously
        List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
                .map(webPageLink -> downloadWebPage(webPageLink)).collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture
                .allOf(pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()]));

        // When all the Futures are completed, call `future.join()` to get their results
        // and collect the results in a list
        CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
            return pageContentFutures.stream().map(pageContentFuture -> pageContentFuture.join())
                    .collect(Collectors.toList());
        });

        // Count the number of web pages having the "CompletableFuture" keyword.
        CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents -> {
            return pageContents.stream().filter(pageContent -> pageContent.contains("CompletableFuture")).count();
        });

        System.out.println("Number of Web Pages having CompletableFuture keyword: " + countFuture.get());
    }

    public static CompletableFuture<String> downloadWebPage(String pageLink) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Downloading: " + pageLink);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Code to download and return the web page's content
            return "CompletableFuture Completed";
        });*/
