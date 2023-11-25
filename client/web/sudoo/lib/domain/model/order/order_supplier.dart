import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/model/order/order_status.dart';
import 'package:sudoo/domain/model/order/shipment.dart';
import 'package:sudoo/domain/model/promotion/promotion_info.dart';

import '../discovery/order_cart_product.dart';

part 'order_supplier.g.dart';

@JsonSerializable(explicitToJson: true)
class OrderSupplier {
  final String orderSupplierId;
  final Supplier supplier;
  final PromotionInfo? promotion;
  final double totalPrice;
  final Shipment shipment;
  final DateTime expectedReceiveDateTime;
  OrderStatus status;
  final List<OrderCartProduct> orderCartProducts;

  OrderSupplier(
    this.orderSupplierId,
    this.supplier,
    this.promotion,
    this.totalPrice,
    this.shipment,
    this.expectedReceiveDateTime,
    this.status,
    this.orderCartProducts,
  );

  factory OrderSupplier.fromJson(Map<String, dynamic> json) => _$OrderSupplierFromJson(json);

  Map<String, dynamic> toJson() => _$OrderSupplierToJson(this);
}
