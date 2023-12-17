
import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/pagination.dart';

part 'pagination_dto.g.dart';

@JsonSerializable()
class PaginationDto {
  final int offset;
  final int total;

  PaginationDto(this.offset, this.total);

  factory PaginationDto.fromJson(Map<String, dynamic> json) => _$PaginationDtoFromJson(json);

  Map<String, dynamic> toJson() => _$PaginationDtoToJson(this);

}

extension Mapper on PaginationDto {
  Pagination toPagination() {
    return Pagination(offset, total);
  }
}