import 'package:json_annotation/json_annotation.dart';

part 'base_response.g.dart';

typedef TypeParser<T> = T Function(Object? json);
typedef JsonParser<T> = Object Function(T value);

@JsonSerializable(genericArgumentFactories: true, explicitToJson: true)
class BaseResponse<Data> {
  final bool success;
  final String message;
  final Data? data;

  BaseResponse(this.success, this.message, this.data);

  factory BaseResponse.fromJson(
          Map<String, dynamic> json, TypeParser<Data> fromJson) =>
      _$BaseResponseFromJson(json, fromJson);

  Map<String, dynamic> toJson(JsonParser<Data> toJson) =>
      _$BaseResponseToJson(this, toJson);
}
