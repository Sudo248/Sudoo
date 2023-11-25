
import 'package:json_annotation/json_annotation.dart';

part 'promotion_info.g.dart';

@JsonSerializable()
class PromotionInfo {
  final String promotionId;
  final String name;
  final double value;

  PromotionInfo(this.promotionId, this.name, this.value);

  factory PromotionInfo.fromJson(Map<String, dynamic> json) => _$PromotionInfoFromJson(json);

  Map<String, dynamic> toJson() => _$PromotionInfoToJson(this);
}