import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/order/order_status.dart';

part 'order_supplier_info.g.dart';

@JsonSerializable()
class OrderSupplierInfo {
  final String orderSupplierId;
  final String supplierId;
  final String supplierName;
  final String userFullName;
  final String userPhoneNumber;
  final String? paymentType;
  final DateTime? paymentDateTime;
  OrderStatus status;
  final String address;
  final DateTime expectedReceiveDateTime;
  final double totalPrice;
  final DateTime createdAt;

  OrderSupplierInfo(
    this.orderSupplierId,
    this.supplierId,
    this.supplierName,
    this.userFullName,
    this.userPhoneNumber,
    this.paymentType,
    this.paymentDateTime,
    this.status,
    this.address,
    this.expectedReceiveDateTime,
    this.totalPrice,
    this.createdAt,
  );

  factory OrderSupplierInfo.fromJson(Map<String, dynamic> json) => _$OrderSupplierInfoFromJson(json);

  Map<String, dynamic> toJson() => _$OrderSupplierInfoToJson(this);
}
