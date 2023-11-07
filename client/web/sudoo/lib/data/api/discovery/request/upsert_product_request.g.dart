// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upsert_product_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UpsertProductRequest _$UpsertProductRequestFromJson(
        Map<String, dynamic> json) =>
    UpsertProductRequest(
      json['productId'] as String?,
      json['sku'] as String?,
      json['name'] as String?,
      json['description'] as String?,
      (json['price'] as num?)?.toDouble(),
      (json['listedPrice'] as num?)?.toDouble(),
      json['amount'] as int?,
      json['soldAmount'] as int?,
      json['discount'] as int?,
      json['startDateDiscount'] == null
          ? null
          : DateTime.parse(json['startDateDiscount'] as String),
      json['endDateDiscount'] == null
          ? null
          : DateTime.parse(json['endDateDiscount'] as String),
      json['saleable'] as bool?,
      json['weight'] as int,
      json['height'] as int,
      json['width'] as int,
      json['length'] as int,
      (json['images'] as List<dynamic>?)
          ?.map((e) => UpsertFileRequest.fromJson(e as Map<String, dynamic>))
          .toList(),
      (json['categoryIds'] as List<dynamic>?)?.map((e) => e as String).toList(),
      json['extras'] == null
          ? null
          : UpsertExtrasRequest.fromJson(
              json['extras'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$UpsertProductRequestToJson(
        UpsertProductRequest instance) =>
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
      'weight': instance.weight,
      'height': instance.height,
      'width': instance.width,
      'length': instance.length,
      'images': instance.images?.map((e) => e.toJson()).toList(),
      'categoryIds': instance.categoryIds,
      'extras': instance.extras?.toJson(),
    };
