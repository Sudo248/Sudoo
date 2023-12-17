import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/user/location.dart';

class Supplier {
  final String supplierId;
  final String name;
  final String avatar;
  final String brand;
  final Location? location;
  final String contactUrl;
  final String locationName;
  final int? totalProducts;
  final double? rate;
  final List<Product>? products;

  const Supplier(
    this.supplierId,
    this.name,
    this.avatar,
    this.brand,
    this.location,
    this.contactUrl,
    this.locationName,
    this.totalProducts,
    this.rate, {
    this.products,
  });
}
