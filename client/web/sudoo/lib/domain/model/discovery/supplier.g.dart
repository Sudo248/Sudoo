// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'supplier.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Supplier _$SupplierFromJson(Map<String, dynamic> json) => Supplier(
      json['supplierId'] as String,
      json['ghnShopId'] as int,
      json['phoneNumber'] as String?,
      json['name'] as String,
      json['avatar'] as String,
      json['brand'] as String,
      json['contactUrl'] as String,
      json['totalProducts'] as int?,
      (json['rate'] as num?)?.toDouble(),
      json['createAt'] == null
          ? null
          : DateTime.parse(json['createAt'] as String),
      Address.fromJson(json['address'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$SupplierToJson(Supplier instance) => <String, dynamic>{
      'supplierId': instance.supplierId,
      'ghnShopId': instance.ghnShopId,
      'phoneNumber': instance.phoneNumber,
      'name': instance.name,
      'avatar': instance.avatar,
      'brand': instance.brand,
      'contactUrl': instance.contactUrl,
      'totalProducts': instance.totalProducts,
      'rate': instance.rate,
      'createAt': instance.createAt?.toIso8601String(),
      'address': instance.address.toJson(),
    };
