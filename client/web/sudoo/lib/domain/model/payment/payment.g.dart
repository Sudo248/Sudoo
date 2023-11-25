// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'payment.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Payment _$PaymentFromJson(Map<String, dynamic> json) => Payment(
      json['paymentId'] as String,
      (json['amount'] as num).toDouble(),
      json['paymentType'] as String,
      DateTime.parse(json['paymentDateTime'] as String),
      $enumDecode(_$PaymentStatusEnumMap, json['status']),
    );

Map<String, dynamic> _$PaymentToJson(Payment instance) => <String, dynamic>{
      'paymentId': instance.paymentId,
      'amount': instance.amount,
      'paymentType': instance.paymentType,
      'paymentDateTime': instance.paymentDateTime.toIso8601String(),
      'status': _$PaymentStatusEnumMap[instance.status]!,
    };

const _$PaymentStatusEnumMap = {
  PaymentStatus.init: 'INIT',
  PaymentStatus.pending: 'PENDING',
  PaymentStatus.success: 'SUCCESS',
  PaymentStatus.failure: 'FAILURE',
};
