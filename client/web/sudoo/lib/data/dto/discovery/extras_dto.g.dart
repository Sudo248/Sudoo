// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'extras_dto.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ExtrasDto _$ExtrasDtoFromJson(Map<String, dynamic> json) => ExtrasDto(
      enable3DViewer: json['enable3DViewer'] as bool? ?? false,
      enableArViewer: json['enableArViewer'] as bool? ?? false,
      source: json['source'] as String?,
    );

Map<String, dynamic> _$ExtrasDtoToJson(ExtrasDto instance) => <String, dynamic>{
      'enable3DViewer': instance.enable3DViewer,
      'enableArViewer': instance.enableArViewer,
      'source': instance.source,
    };
