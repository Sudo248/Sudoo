import 'package:json_annotation/json_annotation.dart';

part 'promotion.g.dart';

@JsonSerializable()
class Promotion {
  String promotionId;
  String? supplierId;
  String name;
  double value;
  String image;
  bool? enable;
  int totalAmount;

  Promotion(this.promotionId, this.supplierId, this.name, this.value, this.image, this.totalAmount, [this.enable]);

  factory Promotion.empty() {
    return Promotion("", null, "", 0, "",0);
  }

  factory Promotion.fromJson(Map<String, dynamic> json) => _$PromotionFromJson(json);

  Map<String, dynamic> toJson() => _$PromotionToJson(this);
}
