// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_cart_product.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderCartProduct _$OrderCartProductFromJson(Map<String, dynamic> json) =>
    OrderCartProduct(
      json['cartProductId'] as String,
      json['cartId'] as String,
      json['quantity'] as int,
      (json['totalPrice'] as num).toDouble(),
      OrderProductInfo.fromJson(json['product'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$OrderCartProductToJson(OrderCartProduct instance) =>
    <String, dynamic>{
      'cartProductId': instance.cartProductId,
      'cartId': instance.cartId,
      'quantity': instance.quantity,
      'totalPrice': instance.totalPrice,
      'product': instance.product.toJson(),
    };
