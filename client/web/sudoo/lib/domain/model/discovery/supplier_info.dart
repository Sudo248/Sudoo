import 'package:sudoo/domain/model/discovery/product.dart';

class SupplierInfo {
  final String supplierId;
  final String name;
  final String avatar;
  final String brand;
  final String contactUrl;
  final int? totalProducts;
  final double? rate;
  final List<Product>? products;

  const SupplierInfo(
    this.supplierId,
    this.name,
    this.avatar,
    this.brand,
    this.contactUrl,
    this.totalProducts,
    this.rate, {
    this.products,
  });
}
