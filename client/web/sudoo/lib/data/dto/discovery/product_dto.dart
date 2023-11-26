import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/dto/discovery/category_dto.dart';
import 'package:sudoo/data/dto/discovery/extras_dto.dart';
import 'package:sudoo/data/dto/discovery/file_dto.dart';
import 'package:sudoo/data/dto/discovery/supplier_dto.dart';
import 'package:sudoo/domain/model/discovery/product.dart';

part 'product_dto.g.dart';

@JsonSerializable(explicitToJson: true)
class ProductDto {
  final String productId;
  final String sku;
  final String name;
  final String brand;
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
  final int weight;
  final int height;
  final int width;
  final int length;
  final List<FileDto> images;
  final SupplierDto supplier;
  final List<CategoryDto> categories;
  final ExtrasDto extras;

  const ProductDto(
    this.productId,
    this.sku,
    this.name,
    this.brand,
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
    this.weight,
    this.height,
    this.width,
    this.length,
    this.images,
    this.supplier,
    this.categories,
    this.extras,
  );

  factory ProductDto.fromJson(Map<String, dynamic> json) =>
      _$ProductDtoFromJson(json);

  Map<String, dynamic> toJson() => _$ProductDtoToJson(this);
}

extension Mapper on ProductDto {
  Product toProduct() {
    return Product(
        productId,
        sku,
        name,
        brand,
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
        weight,
        height,
        width,
        length,
        images.map((e) => e.toFile()).toList(),
        supplier.toSupplierInfo(),
        categories.map((e) => e.toCategory()).toList(),
        extras.toExtras());
  }
}
