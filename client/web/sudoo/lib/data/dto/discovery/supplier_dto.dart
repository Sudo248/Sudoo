import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';

import '../../../domain/model/user/location.dart';

part 'supplier_dto.g.dart';

@JsonSerializable()
class SupplierDto {
  final String supplierId;
  final String name;
  final String avatar;
  final String brand;
  final Location? location;
  final String contactUrl;
  final String locationName;
  final int? totalProducts;
  final double? rate;

  const SupplierDto(
    this.supplierId,
    this.name,
    this.avatar,
    this.brand,
    this.location,
    this.contactUrl,
    this.locationName,
    this.totalProducts,
    this.rate,
  );

  factory SupplierDto.fromJson(Map<String, dynamic> json) => _$SupplierDtoFromJson(json);

  Map<String, dynamic> toJson() => _$SupplierDtoToJson(this);

}

extension Mapper on SupplierDto {
  Supplier toSupplier() {
    return Supplier(
      supplierId,
      name,
      avatar,
      brand,
      location,
      contactUrl,
      locationName,
      totalProducts,
      rate,
    );
  }
}
