import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/user/location.dart';

class Supplier {
  final String supplierId;
  final String name;
  final String avatar;
  final Location? location;
  final List<Product>? products;

  const Supplier({
    this.supplierId = "",
    required this.name,
    required this.avatar,
    this.location,
    this.products,
  });


}
