import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/supplier_info.dart';

part 'supplier_dto.g.dart';

@JsonSerializable()
class SupplierDto {
  final String supplierId;
  final String name;
  final String avatar;
  final String contactUrl;
  final int? totalProducts;
  final double? rate;

  const SupplierDto(
    this.supplierId,
    this.name,
    this.avatar,
    this.contactUrl,
    this.totalProducts,
    this.rate,
  );

  factory SupplierDto.fromJson(Map<String, dynamic> json) => _$SupplierDtoFromJson(json);

  Map<String, dynamic> toJson() => _$SupplierDtoToJson(this);

}

extension Mapper on SupplierDto {
  SupplierInfo toSupplierInfo() {
    return SupplierInfo(
      supplierId,
      name,
      avatar,
      contactUrl,
      totalProducts,
      rate,
    );
  }
}
