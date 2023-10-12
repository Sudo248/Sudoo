// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upsert_image_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UpsertImageRequest _$UpsertImageRequestFromJson(Map<String, dynamic> json) =>
    UpsertImageRequest(
      json['imageId'] as String?,
      json['ownerId'] as String?,
      json['url'] as String,
    );

Map<String, dynamic> _$UpsertImageRequestToJson(UpsertImageRequest instance) =>
    <String, dynamic>{
      'imageId': instance.imageId,
      'ownerId': instance.ownerId,
      'url': instance.url,
    };
