// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'transaction.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Transaction _$TransactionFromJson(Map<String, dynamic> json) => Transaction(
      json['transactionId'] as String,
      json['ownerId'] as String,
      (json['amount'] as num).toDouble(),
      json['description'] as String,
    );

Map<String, dynamic> _$TransactionToJson(Transaction instance) =>
    <String, dynamic>{
      'transactionId': instance.transactionId,
      'ownerId': instance.ownerId,
      'amount': instance.amount,
      'description': instance.description,
    };
