// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_supplier.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderSupplier _$OrderSupplierFromJson(Map<String, dynamic> json) =>
    OrderSupplier(
      json['orderSupplierId'] as String,
      Supplier.fromJson(json['supplier'] as Map<String, dynamic>),
      json['promotion'] == null
          ? null
          : PromotionInfo.fromJson(json['promotion'] as Map<String, dynamic>),
      (json['totalPrice'] as num).toDouble(),
      Shipment.fromJson(json['shipment'] as Map<String, dynamic>),
      DateTime.parse(json['expectedReceiveDateTime'] as String),
      $enumDecode(_$OrderStatusEnumMap, json['status']),
      (json['orderCartProducts'] as List<dynamic>)
          .map((e) => OrderCartProduct.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$OrderSupplierToJson(OrderSupplier instance) =>
    <String, dynamic>{
      'orderSupplierId': instance.orderSupplierId,
      'supplier': instance.supplier.toJson(),
      'promotion': instance.promotion?.toJson(),
      'totalPrice': instance.totalPrice,
      'shipment': instance.shipment.toJson(),
      'expectedReceiveDateTime':
          instance.expectedReceiveDateTime.toIso8601String(),
      'status': _$OrderStatusEnumMap[instance.status]!,
      'orderCartProducts':
          instance.orderCartProducts.map((e) => e.toJson()).toList(),
    };

const _$OrderStatusEnumMap = {
  OrderStatus.prepare: 'PREPARE',
  OrderStatus.ready: 'READY',
  OrderStatus.takeOrder: 'TAKE_ORDER',
  OrderStatus.shipping: 'SHIPPING',
  OrderStatus.received: 'RECEIVED',
  OrderStatus.canceled: 'CANCELED',
};
