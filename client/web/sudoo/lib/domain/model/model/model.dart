
import 'package:json_annotation/json_annotation.dart';

part 'model.g.dart';

@JsonSerializable()
class Model {
  final String version;
  final double evaluate;
  @JsonKey(name: "build_at")
  final DateTime buildAt;
  @JsonKey(name: "user_size")
  final int userSize;
  @JsonKey(name: "product_size")
  final int productSize;
  @JsonKey(name: "category_size")
  final int categorySize;
  @JsonKey(name: "rating_size")
  final int reviewSize;

  Model(this.version, this.evaluate, this.buildAt, this.userSize, this.productSize, this.categorySize, this.reviewSize);

  factory Model.fromJson(Map<String, dynamic> json) => _$ModelFromJson(json);

  Map<String, dynamic> toJson() => _$ModelToJson(this);
}