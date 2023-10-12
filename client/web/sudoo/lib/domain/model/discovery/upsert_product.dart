import 'package:sudoo/domain/model/discovery/upsert_image.dart';

class UpsertProduct {
  final String? productId;
  final String? sku;
  final String? name;
  final String? description;
  final double? price;
  final double? listedPrice;
  final int? amount;
  final int? soldAmount;
  final int? discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool? saleable;
  final List<UpsertImage>? images;
  final List<String>? categoryIds;

  const UpsertProduct({
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
  });
}
