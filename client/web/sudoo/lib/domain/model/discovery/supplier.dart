import 'package:json_annotation/json_annotation.dart';
import 'package:sudoo/data/api/base_request.dart';
import 'package:sudoo/domain/model/user/address.dart';

part 'supplier.g.dart';

@JsonSerializable(explicitToJson: true)
class Supplier implements BaseRequest{
  String supplierId;
  int ghnShopId;
  String? phoneNumber;
  String name;
  String avatar;
  // String brand;
  String contactUrl;
  int? totalProducts;
  double? rate;
  DateTime? createAt;
  Address address;

  Supplier(
    this.supplierId,
    this.ghnShopId,
    this.phoneNumber,
    this.name,
    this.avatar,
    // this.brand,
    this.contactUrl,
    this.totalProducts,
    this.rate,
    this.createAt,
    this.address,
  );

  factory Supplier.empty() {
    return Supplier(
      "",
      0,
      null,
      "",
      "",
      "",
      // "",
      0,
      0,
      DateTime.now(),
      Address.empty(),
    );
  }

  factory Supplier.fromJson(Map<String, dynamic> json) => _$SupplierFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$SupplierToJson(this);
}
