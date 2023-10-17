// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'extras_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ExtrasDto _$ExtrasDtoFromJson(Map<String, dynamic> json) => ExtrasDto(
      enable3DViewer: json['enable3DViewer'] as bool? ?? false,
      enableARViewer: json['enableARViewer'] as bool? ?? false,
      source: json['source'] == null
          ? null
          : FileDto.fromJson(json['source'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ExtrasDtoToJson(ExtrasDto instance) => <String, dynamic>{
      'enable3DViewer': instance.enable3DViewer,
      'enableARViewer': instance.enableARViewer,
      'source': instance.source,
    };
