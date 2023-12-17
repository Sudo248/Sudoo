import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/category.dart';

part 'category_dto.g.dart';

@JsonSerializable()
class CategoryDto {
  final String categoryId;
  final String name;
  final String image;

  const CategoryDto(this.categoryId, this.name, this.image);

  factory CategoryDto.fromJson(Map<String, dynamic> json) => _$CategoryDtoFromJson(json);

  Map<String, dynamic> toJson() => _$CategoryDtoToJson(this);

}

extension Mapper on CategoryDto {
  Category toCategory() {
    return Category(
      categoryId,
      name,
      image,
    );
  }
}
