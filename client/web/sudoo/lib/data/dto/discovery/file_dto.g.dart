// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'file_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

FileDto _$FileDtoFromJson(Map<String, dynamic> json) => FileDto(
      json['imageId'] as String,
      json['ownerId'] as String,
      json['url'] as String,
    );

Map<String, dynamic> _$FileDtoToJson(FileDto instance) => <String, dynamic>{
      'imageId': instance.fileId,
      'ownerId': instance.ownerId,
      'url': instance.url,
    };
