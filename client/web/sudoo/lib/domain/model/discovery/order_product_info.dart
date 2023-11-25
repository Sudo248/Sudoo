import 'package:json_annotation/json_annotation.dart';

part 'order_product_info.g.dart';

@JsonSerializable()
class OrderProductInfo {
  final String productId;
  final String supplierId;
  final String sku;
  final String name;
  final String brand;
  final double price;
  final double weight, height, length, width;
  final List<String> images;

  OrderProductInfo(
    this.productId,
    this.supplierId,
    this.sku,
    this.name,
    this.brand,
    this.price,
    this.weight,
    this.height,
    this.length,
    this.width,
    this.images,
  );

  factory OrderProductInfo.fromJson(Map<String, dynamic> json) => _$OrderProductInfoFromJson(json);

  Map<String, dynamic> toJson() => _$OrderProductInfoToJson(this);
}
