import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/dto/discovery/category_dto.dart';
import 'package:sudoo/data/dto/discovery/image_dto.dart';
import 'package:sudoo/data/dto/discovery/supplier_dto.dart';
import 'package:sudoo/domain/model/discovery/product.dart';

part 'product_dto.g.dart';

@JsonSerializable()
class ProductDto {
  final String productId;
  final String sku;
  final String name;
  final String description;
  final double price;
  final double listedPrice;
  final int amount;
  final int soldAmount;
  final double rate;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;
  final List<ImageDto> images;
  final SupplierDto supplier;
  final List<CategoryDto> categories;

  const ProductDto(
    this.productId,
    this.sku,
    this.name,
    this.description,
    this.price,
    this.listedPrice,
    this.amount,
    this.soldAmount,
    this.rate,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.images,
    this.supplier,
    this.categories,
  );

  factory ProductDto.fromJson(Map<String, dynamic> json) => _$ProductDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ProductDtoToJson(this);
}

extension Mapper on ProductDto {
  Product toProduct() {
    return Product(
      productId,
      sku,
      name,
      description,
      price,
      listedPrice,
      amount,
      soldAmount,
      rate,
      discount,
      startDateDiscount,
      endDateDiscount,
      saleable,
      images.map((e) => e.toImage()).toList(),
      supplier.toSupplier(),
      categories.map((e) => e.toCategory()).toList(),
    );
  }
}
