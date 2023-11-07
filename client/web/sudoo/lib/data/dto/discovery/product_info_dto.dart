import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';

part 'product_info_dto.g.dart';

@JsonSerializable(explicitToJson: true)
class ProductInfoDto {
  final String productId;
  final String sku;
  final String name;
  final double price;
  final double listedPrice;
  final int amount;
  final double rate;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;
  final String brand;
  final List<String> images;

  const ProductInfoDto(
    this.productId,
    this.sku,
    this.name,
    this.price,
    this.listedPrice,
    this.amount,
    this.rate,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.brand,
    this.images,
  );

  factory ProductInfoDto.fromJson(Map<String, dynamic> json) =>
      _$ProductInfoDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ProductInfoDtoToJson(this);
}

extension Mapper on ProductInfoDto {
  ProductInfo toProductInfo() {
    return ProductInfo(
      productId,
      sku,
      name,
      price,
      listedPrice,
      amount,
      rate,
      discount,
      startDateDiscount,
      endDateDiscount,
      saleable,
      brand,
      images,
    );
  }
}
