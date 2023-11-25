// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_product_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderProductInfo _$OrderProductInfoFromJson(Map<String, dynamic> json) =>
    OrderProductInfo(
      json['productId'] as String,
      json['supplierId'] as String,
      json['sku'] as String,
      json['name'] as String,
      json['brand'] as String,
      (json['price'] as num).toDouble(),
      (json['weight'] as num).toDouble(),
      (json['height'] as num).toDouble(),
      (json['length'] as num).toDouble(),
      (json['width'] as num).toDouble(),
      (json['images'] as List<dynamic>).map((e) => e as String).toList(),
    );

Map<String, dynamic> _$OrderProductInfoToJson(OrderProductInfo instance) =>
    <String, dynamic>{
      'productId': instance.productId,
      'supplierId': instance.supplierId,
      'sku': instance.sku,
      'name': instance.name,
      'brand': instance.brand,
      'price': instance.price,
      'weight': instance.weight,
      'height': instance.height,
      'length': instance.length,
      'width': instance.width,
      'images': instance.images,
    };
