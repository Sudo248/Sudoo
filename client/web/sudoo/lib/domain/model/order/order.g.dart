// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Order _$OrderFromJson(Map<String, dynamic> json) => Order(
      json['orderId'] as String,
      json['cartId'] as String,
      Payment.fromJson(json['payment'] as Map<String, dynamic>),
      PromotionInfo.fromJson(json['promotion'] as Map<String, dynamic>),
      User.fromJson(json['user'] as Map<String, dynamic>),
      json['address'] as String,
      (json['totalPrice'] as num).toDouble(),
      (json['totalPromotionPrice'] as num).toDouble(),
      (json['finalPrice'] as num).toDouble(),
      (json['totalShipmentPrice'] as num).toDouble(),
      DateTime.parse(json['createdAt'] as String),
      (json['orderSuppliers'] as List<dynamic>)
          .map((e) => OrderSupplier.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$OrderToJson(Order instance) => <String, dynamic>{
      'orderId': instance.orderId,
      'cartId': instance.cartId,
      'payment': instance.payment.toJson(),
      'promotion': instance.promotion.toJson(),
      'user': instance.user.toJson(),
      'address': instance.address,
      'totalPrice': instance.totalPrice,
      'totalPromotionPrice': instance.totalPromotionPrice,
      'finalPrice': instance.finalPrice,
      'totalShipmentPrice': instance.totalShipmentPrice,
      'createdAt': instance.createdAt.toIso8601String(),
      'orderSuppliers': instance.orderSuppliers.map((e) => e.toJson()).toList(),
    };
