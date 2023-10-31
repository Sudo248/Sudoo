// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'upsert_extras_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UpsertExtrasRequest _$UpsertExtrasRequestFromJson(Map<String, dynamic> json) =>
    UpsertExtrasRequest(
      json['enable3DViewer'] as bool,
      json['enableArViewer'] as bool,
      json['source'] as String?,
    );

Map<String, dynamic> _$UpsertExtrasRequestToJson(
        UpsertExtrasRequest instance) =>
    <String, dynamic>{
      'enable3DViewer': instance.enable3DViewer,
      'enableArViewer': instance.enableArViewer,
      'source': instance.source,
    };
