// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'base_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BaseResponse<Data> _$BaseResponseFromJson<Data>(
  Map<String, dynamic> json,
  Data Function(Object? json) fromJsonData,
) =>
    BaseResponse<Data>(
      json['success'] as bool,
      json['message'] as String,
      _$nullableGenericFromJson(json['data'], fromJsonData),
    );

Map<String, dynamic> _$BaseResponseToJson<Data>(
  BaseResponse<Data> instance,
  Object? Function(Data value) toJsonData,
) =>
    <String, dynamic>{
      'success': instance.success,
      'message': instance.message,
      'data': _$nullableGenericToJson(instance.data, toJsonData),
    };

T? _$nullableGenericFromJson<T>(
  Object? input,
  T Function(Object? json) fromJson,
) =>
    input == null ? null : fromJson(input);

Object? _$nullableGenericToJson<T>(
  T? input,
  Object? Function(T value) toJson,
) =>
    input == null ? null : toJson(input);
