import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';

part 'category_product_dto.g.dart';

@JsonSerializable()
class CategoryProductDto {
  final String categoryProductId;
  final String productId;
  final String categoryId;

  CategoryProductDto(this.categoryProductId, this.productId, this.categoryId);

  factory CategoryProductDto.fromJson(Map<String, dynamic> json) => _$CategoryProductDtoFromJson(json);

  Map<String, dynamic> toJson() => _$CategoryProductDtoToJson(this);
}

extension Mapper on CategoryProductDto {
  CategoryProduct toCategoryProduct() {
    return CategoryProduct(
      categoryProductId: categoryProductId,
      productId: productId,
      categoryId: categoryId,
    );
  }
}
