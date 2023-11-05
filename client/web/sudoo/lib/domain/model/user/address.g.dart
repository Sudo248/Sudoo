// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'address.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Address _$AddressFromJson(Map<String, dynamic> json) => Address(
      addressId: json['addressId'] as String,
      provinceID: json['provinceID'] as int,
      districtID: json['districtID'] as int,
      wardCode: json['wardCode'] as String,
      provinceName: json['provinceName'] as String,
      districtName: json['districtName'] as String,
      wardName: json['wardName'] as String,
      address: json['address'] as String,
      fullAddress: json['fullAddress'] as String,
    );

Map<String, dynamic> _$AddressToJson(Address instance) => <String, dynamic>{
      'addressId': instance.addressId,
      'provinceID': instance.provinceID,
      'districtID': instance.districtID,
      'wardCode': instance.wardCode,
      'provinceName': instance.provinceName,
      'districtName': instance.districtName,
      'wardName': instance.wardName,
      'address': instance.address,
      'fullAddress': instance.fullAddress,
    };
