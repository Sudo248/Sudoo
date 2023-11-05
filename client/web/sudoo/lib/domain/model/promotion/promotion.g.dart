// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'promotion.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Promotion _$PromotionFromJson(Map<String, dynamic> json) => Promotion(
      json['promotionId'] as String,
      json['supplierId'] as String?,
      json['name'] as String,
      (json['value'] as num).toDouble(),
      json['image'] as String,
      json['totalAmount'] as int,
      json['enable'] as bool?,
    );

Map<String, dynamic> _$PromotionToJson(Promotion instance) => <String, dynamic>{
      'promotionId': instance.promotionId,
      'supplierId': instance.supplierId,
      'name': instance.name,
      'value': instance.value,
      'image': instance.image,
      'enable': instance.enable,
      'totalAmount': instance.totalAmount,
    };
