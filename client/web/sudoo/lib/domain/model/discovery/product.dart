import 'package:sudoo/domain/model/discovery/image.dart';

import 'category.dart';
import 'supplier.dart';

class Product {
  final String productId;
  final String sku;
  final String name;
  final String description;
  double price;
  double listedPrice;
  final int amount;
  final int soldAmount;
  final double rate;
  int discount;
  final DateTime? startDateDiscount;
  DateTime? endDateDiscount;
  bool saleable;
  final List<Image> images;
  final Supplier supplier;
  final List<Category> categories;

  Product(
    this.productId,
    this.sku,
    this.name,
    this.description,
    this.price,
    this.listedPrice,
    this.amount,
    this.soldAmount,
    this.rate,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
    this.images,
    this.supplier,
    this.categories,
  );
}
