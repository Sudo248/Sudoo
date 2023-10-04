// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'supplier_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SupplierDto _$SupplierDtoFromJson(Map<String, dynamic> json) => SupplierDto(
      json['supplierId'] as String,
      json['name'] as String,
      json['avatar'] as String,
      json['brand'] as String,
      json['location'] == null
          ? null
          : Location.fromJson(json['location'] as Map<String, dynamic>),
      json['contactUrl'] as String,
      json['locationName'] as String,
      json['totalProducts'] as int?,
      (json['rate'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$SupplierDtoToJson(SupplierDto instance) =>
    <String, dynamic>{
      'supplierId': instance.supplierId,
      'name': instance.name,
      'avatar': instance.avatar,
      'brand': instance.brand,
      'location': instance.location,
      'contactUrl': instance.contactUrl,
      'locationName': instance.locationName,
      'totalProducts': instance.totalProducts,
      'rate': instance.rate,
    };
