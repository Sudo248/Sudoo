import 'package:sudoo/domain/model/discovery/category.dart';

class ProductInfo {
  final String productId;
  final String sku;
  final String name;
  final double price;
  final double listedPrice;
  final int amount;
  final double rate;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;
  final String brand;
  final List<String> images;
  List<Category>? categories;

  const ProductInfo(
    this.productId,
    this.sku,
    this.name,
    this.price,
    this.listedPrice,
    this.amount,
    this.rate,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.brand,
    this.images,
  );
}
