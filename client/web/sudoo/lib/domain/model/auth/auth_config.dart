import 'package:json_annotation/json_annotation.dart';

part 'auth_config.g.dart';

@JsonSerializable()
class AuthConfig {
  final bool enableOtp;

  AuthConfig(this.enableOtp);

  factory AuthConfig.fromJson(Map<String, dynamic> json) => _$AuthConfigFromJson(json);

  Map<String, dynamic> toJson() => _$AuthConfigToJson(this);
}