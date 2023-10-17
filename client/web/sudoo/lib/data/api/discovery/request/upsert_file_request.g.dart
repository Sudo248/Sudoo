// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upsert_file_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UpsertFileRequest _$UpsertFileRequestFromJson(Map<String, dynamic> json) =>
    UpsertFileRequest(
      json['imageId'] as String?,
      json['ownerId'] as String?,
      json['url'] as String,
    );

Map<String, dynamic> _$UpsertFileRequestToJson(UpsertFileRequest instance) =>
    <String, dynamic>{
      'imageId': instance.imageId,
      'ownerId': instance.ownerId,
      'url': instance.url,
    };
