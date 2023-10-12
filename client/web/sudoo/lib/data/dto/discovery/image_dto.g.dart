// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'image_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ImageDto _$ImageDtoFromJson(Map<String, dynamic> json) => ImageDto(
      json['imageId'] as String,
      json['ownerId'] as String,
      json['url'] as String,
    );

Map<String, dynamic> _$ImageDtoToJson(ImageDto instance) => <String, dynamic>{
      'imageId': instance.imageId,
      'ownerId': instance.ownerId,
      'url': instance.url,
    };
