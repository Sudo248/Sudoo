// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'category_product_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CategoryProductDto _$CategoryProductDtoFromJson(Map<String, dynamic> json) =>
    CategoryProductDto(
      json['categoryProductId'] as String,
      json['productId'] as String,
      json['categoryId'] as String,
    );

Map<String, dynamic> _$CategoryProductDtoToJson(CategoryProductDto instance) =>
    <String, dynamic>{
      'categoryProductId': instance.categoryProductId,
      'productId': instance.productId,
      'categoryId': instance.categoryId,
    };
