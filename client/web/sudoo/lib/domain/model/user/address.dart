import 'package:sudoo/domain/model/user/location.dart';

class Address {
  final String addressId;
  final int provinceId;
  final int districtId;
  final String wardCode;
  final String provinceName;
  final String districtName;
  final String wardName;
  final String address;
  final Location location;
  final String fullAddress;

  Address({
    required this.addressId,
    required this.provinceId,
    required this.districtId,
    required this.wardCode,
    required this.provinceName,
    required this.districtName,
    required this.wardName,
    required this.address,
    required this.location,
    required this.fullAddress,
  });
}
