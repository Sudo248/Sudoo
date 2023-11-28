// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'category_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CategoryDto _$CategoryDtoFromJson(Map<String, dynamic> json) => CategoryDto(
      json['categoryId'] as String,
      json['name'] as String,
      json['image'] as String,
      json['enable'] as bool? ?? true,
      json['countProduct'] as int?,
    );

Map<String, dynamic> _$CategoryDtoToJson(CategoryDto instance) =>
    <String, dynamic>{
      'categoryId': instance.categoryId,
      'name': instance.name,
      'image': instance.image,
      'enable': instance.enable,
      'countProduct': instance.countProduct,
    };
