import 'package:json_annotation/json_annotation.dart';

part 'order_config.g.dart';

@JsonSerializable()
class OrderConfig {
  @JsonKey(name: "enableStaffFullControlOrderStatus")
  final bool enableAllStatus;

  OrderConfig(this.enableAllStatus);

  factory OrderConfig.fromJson(Map<String, dynamic> json) => _$OrderConfigFromJson(json);

  Map<String, dynamic> toJson() => _$OrderConfigToJson(this);
}