import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/dto/discovery/extras_dto.dart';
import 'package:sudoo/data/dto/discovery/file_dto.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';

part 'upsert_product_dto.g.dart';

@JsonSerializable()
class UpsertProductDto {
  final String productId;
  final String sku;
  final String name;
  final String description;
  final double price;
  final double listedPrice;
  final int amount;
  final int? soldAmount;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;
  final List<FileDto>? images;
  final List<String>? categoryIds;
  final ExtrasDto? extras;

  const UpsertProductDto(
    this.productId,
    this.sku,
    this.name,
    this.description,
    this.price,
    this.listedPrice,
    this.amount,
    this.soldAmount,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.images,
    this.categoryIds,
    this.extras,
  );

  factory UpsertProductDto.fromJson(Map<String, dynamic> json) =>
      _$UpsertProductDtoFromJson(json);

  Map<String, dynamic> toJson() => _$UpsertProductDtoToJson(this);
}

extension Mapper on UpsertProductDto {
  UpsertProduct toUpsertProduct() {
    return UpsertProduct(
      productId: productId,
      sku: sku,
      name: name,
      description: description,
      price: price,
      listedPrice: listedPrice,
      amount: amount,
      soldAmount: soldAmount,
      discount: discount,
      startDateDiscount: startDateDiscount,
      endDateDiscount: endDateDiscount,
      saleable: saleable,
      images: images?.map((e) => e.toUpsertFIle()).toList(),
      categoryIds: categoryIds,
    );
  }
}
