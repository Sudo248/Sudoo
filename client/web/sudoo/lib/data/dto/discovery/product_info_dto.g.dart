// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product_info_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductInfoDto _$ProductInfoDtoFromJson(Map<String, dynamic> json) =>
    ProductInfoDto(
      json['productId'] as String,
      json['sku'] as String,
      json['name'] as String,
      (json['price'] as num).toDouble(),
      (json['listedPrice'] as num).toDouble(),
      json['amount'] as int,
      (json['rate'] as num).toDouble(),
      json['discount'] as int,
      json['startDateDiscount'] == null
          ? null
          : DateTime.parse(json['startDateDiscount'] as String),
      json['endDateDiscount'] == null
          ? null
          : DateTime.parse(json['endDateDiscount'] as String),
      json['saleable'] as bool,
      json['brand'] as String,
      (json['images'] as List<dynamic>).map((e) => e as String).toList(),
    );

Map<String, dynamic> _$ProductInfoDtoToJson(ProductInfoDto instance) =>
    <String, dynamic>{
      'productId': instance.productId,
      'sku': instance.sku,
      'name': instance.name,
      'price': instance.price,
      'listedPrice': instance.listedPrice,
      'amount': instance.amount,
      'rate': instance.rate,
      'discount': instance.discount,
      'startDateDiscount': instance.startDateDiscount?.toIso8601String(),
      'endDateDiscount': instance.endDateDiscount?.toIso8601String(),
      'saleable': instance.saleable,
      'brand': instance.brand,
      'images': instance.images,
    };
