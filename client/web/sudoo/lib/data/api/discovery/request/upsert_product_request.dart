import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_extras_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_file_request.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';

part 'upsert_product_request.g.dart';

@JsonSerializable(explicitToJson: true)
class UpsertProductRequest extends BaseRequest {
  final String? productId;
  final String? sku;
  final String? name;
  final String? brand;
  final String? description;
  final double? price;
  final double? listedPrice;
  final int? amount;
  final int? soldAmount;
  final int? discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool? saleable;
  final double weight;
  final double height;
  final double width;
  final double length;
  final List<UpsertFileRequest>? images;
  final List<String>? categoryIds;
  final UpsertExtrasRequest? extras;

  UpsertProductRequest(
    this.productId,
    this.sku,
    this.name,
    this.brand,
    this.description,
    this.price,
    this.listedPrice,
    this.amount,
    this.soldAmount,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.weight,
    this.height,
    this.width,
    this.length,
    this.images,
    this.categoryIds,
    this.extras,
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
      brand,
      description,
      price,
      listedPrice,
      amount,
      soldAmount,
      discount,
      startDateDiscount,
      endDateDiscount,
      saleable,
      weight,
      height,
      width,
      length,
      images?.map((e) => e.toUpsertImageRequest()).toList(),
      categoryIds,
      extras?.toUpsertProductExtraRequest()
    );
  }
}
