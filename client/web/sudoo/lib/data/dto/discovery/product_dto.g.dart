// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductDto _$ProductDtoFromJson(Map<String, dynamic> json) => ProductDto(
      json['productId'] as String,
      json['sku'] as String,
      json['name'] as String,
      json['description'] as String,
      (json['price'] as num).toDouble(),
      (json['listedPrice'] as num).toDouble(),
      json['amount'] as int,
      json['soldAmount'] as int,
      (json['rate'] as num).toDouble(),
      json['discount'] as int,
      json['startDateDiscount'] == null
          ? null
          : DateTime.parse(json['startDateDiscount'] as String),
      json['endDateDiscount'] == null
          ? null
          : DateTime.parse(json['endDateDiscount'] as String),
      json['saleable'] as bool,
      (json['images'] as List<dynamic>).map((e) => e as String).toList(),
      SupplierDto.fromJson(json['supplier'] as Map<String, dynamic>),
      (json['categories'] as List<dynamic>)
          .map((e) => CategoryDto.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$ProductDtoToJson(ProductDto instance) =>
    <String, dynamic>{
      'productId': instance.productId,
      'sku': instance.sku,
      'name': instance.name,
      'description': instance.description,
      'price': instance.price,
      'listedPrice': instance.listedPrice,
      'amount': instance.amount,
      'soldAmount': instance.soldAmount,
      'rate': instance.rate,
      'discount': instance.discount,
      'startDateDiscount': instance.startDateDiscount?.toIso8601String(),
      'endDateDiscount': instance.endDateDiscount?.toIso8601String(),
      'saleable': instance.saleable,
      'images': instance.images,
      'supplier': instance.supplier,
      'categories': instance.categories,
    };
