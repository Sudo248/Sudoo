// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'address_suggestion.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

AddressSuggestion _$AddressSuggestionFromJson(Map<String, dynamic> json) =>
    AddressSuggestion(
      json['addressId'] as int,
      json['addressName'] as String,
      json['addressCode'] as String,
    );

Map<String, dynamic> _$AddressSuggestionToJson(AddressSuggestion instance) =>
    <String, dynamic>{
      'addressId': instance.addressId,
      'addressName': instance.addressName,
      'addressCode': instance.addressCode,
    };
