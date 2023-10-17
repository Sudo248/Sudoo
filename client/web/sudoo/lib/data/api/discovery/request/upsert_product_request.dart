import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_file_request.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';

part 'upsert_product_request.g.dart';

@JsonSerializable()
class UpsertProductRequest extends BaseRequest {
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
  final List<UpsertFileRequest>? images;
  final List<String>? categoryIds;

  UpsertProductRequest(
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
  );

  @override
  Map<String, dynamic> toJson() => _$UpsertProductRequestToJson(this);
}

extension Mapper on UpsertProduct {
  UpsertProductRequest toUpsertProductRequest() {
    return UpsertProductRequest(
      productId,
      sku,
      name,
      description,
      price,
      listedPrice,
      amount,
      soldAmount,
      discount,
      startDateDiscount,
      endDateDiscount,
      saleable,
      images?.map((e) => e.toUpsertImageRequest()).toList(),
      categoryIds,
    );
  }
}
