// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upsert_product_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UpsertProductDto _$UpsertProductDtoFromJson(Map<String, dynamic> json) =>
    UpsertProductDto(
      json['productId'] as String,
      json['sku'] as String,
      json['name'] as String,
      json['description'] as String,
      (json['price'] as num).toDouble(),
      (json['listedPrice'] as num).toDouble(),
      json['amount'] as int,
      json['soldAmount'] as int?,
      json['discount'] as int,
      json['startDateDiscount'] == null
          ? null
          : DateTime.parse(json['startDateDiscount'] as String),
      json['endDateDiscount'] == null
          ? null
          : DateTime.parse(json['endDateDiscount'] as String),
      json['saleable'] as bool,
      (json['images'] as List<dynamic>?)
          ?.map((e) => FileDto.fromJson(e as Map<String, dynamic>))
          .toList(),
      (json['categoryIds'] as List<dynamic>?)?.map((e) => e as String).toList(),
      json['extras'] == null
          ? null
          : ExtrasDto.fromJson(json['extras'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$UpsertProductDtoToJson(UpsertProductDto instance) =>
    <String, dynamic>{
      'productId': instance.productId,
      'sku': instance.sku,
      'name': instance.name,
      'description': instance.description,
      'price': instance.price,
      'listedPrice': instance.listedPrice,
      'amount': instance.amount,
      'soldAmount': instance.soldAmount,
      'discount': instance.discount,
      'startDateDiscount': instance.startDateDiscount?.toIso8601String(),
      'endDateDiscount': instance.endDateDiscount?.toIso8601String(),
      'saleable': instance.saleable,
      'images': instance.images,
      'categoryIds': instance.categoryIds,
      'extras': instance.extras,
    };
