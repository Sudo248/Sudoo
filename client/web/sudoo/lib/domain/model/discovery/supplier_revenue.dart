import 'package:json_annotation/json_annotation.dart';

part 'supplier_revenue.g.dart';

@JsonSerializable()
class SupplierRevenue {
  final double totalRevenue;
  final double income;

  SupplierRevenue(this.totalRevenue, this.income);

  factory SupplierRevenue.fromJson(Map<String, dynamic> json) => _$SupplierRevenueFromJson(json);

  Map<String, dynamic> toJson() => _$SupplierRevenueToJson(this);
}