// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'supplier_revenue.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SupplierRevenue _$SupplierRevenueFromJson(Map<String, dynamic> json) =>
    SupplierRevenue(
      (json['totalRevenue'] as num).toDouble(),
      (json['income'] as num).toDouble(),
    );

Map<String, dynamic> _$SupplierRevenueToJson(SupplierRevenue instance) =>
    <String, dynamic>{
      'totalRevenue': instance.totalRevenue,
      'income': instance.income,
    };
