// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product_pagination_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ProductPaginationDto<T> _$ProductPaginationDtoFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) =>
    ProductPaginationDto<T>(
      (json['products'] as List<dynamic>).map(fromJsonT).toList(),
      PaginationDto.fromJson(json['pagination'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ProductPaginationDtoToJson<T>(
  ProductPaginationDto<T> instance,
  Object? Function(T value) toJsonT,
) =>
    <String, dynamic>{
      'products': instance.products.map(toJsonT).toList(),
      'pagination': instance.pagination.toJson(),
    };
