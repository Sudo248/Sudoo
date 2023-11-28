import 'package:sudoo/domain/model/discovery/file.dart';

import 'category.dart';
import 'extras.dart';
import 'supplier_info.dart';

class Product {
  final String productId;
  final String sku;
  final String name;
  final String brand;
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
  final int weight;
  final int height;
  final int width;
  final int length;
  final List<File> images;
  final SupplierInfo supplier;
  final List<Category> categories;
  final Extras? extras;

  Product(
    this.productId,
    this.sku,
    this.name,
    this.brand,
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
    this.weight,
    this.height,
    this.width,
    this.length,
    this.images,
    this.supplier,
    this.categories,
    this.extras,
  );
}
