// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'promotion_info.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PromotionInfo _$PromotionInfoFromJson(Map<String, dynamic> json) =>
    PromotionInfo(
      json['promotionId'] as String,
      json['name'] as String,
      (json['value'] as num).toDouble(),
    );

Map<String, dynamic> _$PromotionInfoToJson(PromotionInfo instance) =>
    <String, dynamic>{
      'promotionId': instance.promotionId,
      'name': instance.name,
      'value': instance.value,
    };
