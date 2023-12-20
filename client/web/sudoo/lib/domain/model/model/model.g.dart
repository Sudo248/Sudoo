// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Model _$ModelFromJson(Map<String, dynamic> json) => Model(
      json['version'] as String,
      (json['evaluate'] as num).toDouble(),
      DateTime.parse(json['build_at'] as String),
      json['user_size'] as int,
      json['product_size'] as int,
      json['category_size'] as int,
      json['rating_size'] as int,
    );

Map<String, dynamic> _$ModelToJson(Model instance) => <String, dynamic>{
      'version': instance.version,
      'evaluate': instance.evaluate,
      'build_at': instance.buildAt.toIso8601String(),
      'user_size': instance.userSize,
      'product_size': instance.productSize,
      'category_size': instance.categorySize,
      'rating_size': instance.reviewSize,
    };
