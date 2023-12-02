import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/payment/payment.dart';
import 'package:sudoo/domain/model/promotion/promotion_info.dart';
import 'package:sudoo/domain/model/user/user.dart';

import 'order_supplier.dart';

part 'order.g.dart';

@JsonSerializable(explicitToJson: true)
class Order {
  final String orderId;
  final String cartId;
  final Payment payment;
  final PromotionInfo? promotion;
  final User user;
  final String address;
  final double totalPrice;
  final double totalPromotionPrice;
  final double finalPrice;
  final double totalShipmentPrice;
  final DateTime createdAt;
  final List<OrderSupplier> orderSuppliers;

  Order(
    this.orderId,
    this.cartId,
    this.payment,
    this.promotion,
    this.user,
    this.address,
    this.totalPrice,
    this.totalPromotionPrice,
    this.finalPrice,
    this.totalShipmentPrice,
    this.createdAt,
    this.orderSuppliers,
  );

  factory Order.fromJson(Map<String, dynamic> json) => _$OrderFromJson(json);

  Map<String, dynamic> toJson() => _$OrderToJson(this);
}
