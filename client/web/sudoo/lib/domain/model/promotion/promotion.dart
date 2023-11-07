import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';

part 'promotion.g.dart';

@JsonSerializable()
class Promotion implements BaseRequest {
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

  @override
  Map<String, dynamic> toJson() => _$PromotionToJson(this);
}
