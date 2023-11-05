import 'package:json_annotation/json_annotation.dart';

part 'address.g.dart';

@JsonSerializable()
class Address {
  String addressId;
  int provinceID;
  int districtID;
  String wardCode;
  String provinceName;
  String districtName;
  String wardName;
  String address;
  String fullAddress;

  Address({
    required this.addressId,
    required this.provinceID,
    required this.districtID,
    required this.wardCode,
    required this.provinceName,
    required this.districtName,
    required this.wardName,
    required this.address,
    required this.fullAddress,
  });

  factory Address.empty() {
    return Address(
      addressId: "",
      provinceID: 0,
      districtID: 0,
      wardCode: "",
      provinceName: "",
      districtName: "",
      wardName: "",
      address: "",
      fullAddress: "",
    );
  }

  factory Address.fromJson(Map<String, dynamic> json) => _$AddressFromJson(json);

  Map<String, dynamic> toJson() => _$AddressToJson(this);
}
