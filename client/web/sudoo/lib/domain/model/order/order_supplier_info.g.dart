// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_supplier_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderSupplierInfo _$OrderSupplierInfoFromJson(Map<String, dynamic> json) =>
    OrderSupplierInfo(
      json['orderSupplierId'] as String,
      json['supplierId'] as String,
      json['supplierName'] as String,
      json['userFullName'] as String,
      json['userPhoneNumber'] as String,
      json['paymentType'] as String?,
      json['paymentDateTime'] == null
          ? null
          : DateTime.parse(json['paymentDateTime'] as String),
      $enumDecode(_$OrderStatusEnumMap, json['status']),
      json['address'] as String,
      DateTime.parse(json['expectedReceiveDateTime'] as String),
      (json['totalPrice'] as num).toDouble(),
      DateTime.parse(json['createdAt'] as String),
    );

Map<String, dynamic> _$OrderSupplierInfoToJson(OrderSupplierInfo instance) =>
    <String, dynamic>{
      'orderSupplierId': instance.orderSupplierId,
      'supplierId': instance.supplierId,
      'supplierName': instance.supplierName,
      'userFullName': instance.userFullName,
      'userPhoneNumber': instance.userPhoneNumber,
      'paymentType': instance.paymentType,
      'paymentDateTime': instance.paymentDateTime?.toIso8601String(),
      'status': _$OrderStatusEnumMap[instance.status]!,
      'address': instance.address,
      'expectedReceiveDateTime':
          instance.expectedReceiveDateTime.toIso8601String(),
      'totalPrice': instance.totalPrice,
      'createdAt': instance.createdAt.toIso8601String(),
    };

const _$OrderStatusEnumMap = {
  OrderStatus.prepare: 'PREPARE',
  OrderStatus.ready: 'READY',
  OrderStatus.takeOrder: 'TAKE_ORDER',
  OrderStatus.shipping: 'SHIPPING',
  OrderStatus.received: 'RECEIVED',
  OrderStatus.canceled: 'CANCELED',
};
