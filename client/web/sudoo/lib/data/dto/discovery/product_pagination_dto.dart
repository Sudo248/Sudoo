import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/dto/discovery/pagination_dto.dart';
import 'package:sudoo/domain/model/discovery/product_pagination.dart';

import '../../api/base_response.dart';

part 'product_pagination_dto.g.dart';

@JsonSerializable(genericArgumentFactories: true, explicitToJson: true)
class ProductPaginationDto<T> {
  final List<T> products;
  final PaginationDto pagination;

  ProductPaginationDto(this.products, this.pagination);

  factory ProductPaginationDto.fromJson(
          Map<String, dynamic> json, TypeParser<T> fromJson) =>
      _$ProductPaginationDtoFromJson(json, fromJson);

  Map<String, dynamic> toJson(JsonParser<T> toJson) =>
      _$ProductPaginationDtoToJson(this, toJson);
}

extension Mapper<T> on ProductPaginationDto<T> {
  ProductPagination<D> toProductPagination<D>({
    required D Function(T e) converter,
  }) {
    return ProductPagination(
      products.map((e) => converter(e)).toList(),
      pagination.toPagination(),
    );
  }
}
