import 'package:sudoo/domain/model/discovery/upsert_file.dart';

import 'extras.dart';

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
  final List<UpsertFile>? images;
  final List<String>? categoryIds;
  final int weight;
  final int height;
  final int width;
  final int length;
  final Extras? extras;

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
    this.weight = 0,
    this.height = 0,
    this.width = 0,
    this.length = 0,
    this.images,
    this.categoryIds,
    this.extras,
  });
}
