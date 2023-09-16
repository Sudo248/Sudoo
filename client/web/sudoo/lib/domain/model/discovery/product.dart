import 'category.dart';
import 'supplier.dart';

class Product {
  final String productId;
  final String name;
  final String description;
  final String sku;
  final List<String> images;
  final List<Category> categories;
  final Supplier supplier;
  final double price;
  final int amount;
  final int soldAmount;
  final double rate;
  final double listedPrice;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;

  Product(
    this.productId,
    this.name,
    this.description,
    this.sku,
    this.images,
    this.categories,
    this.supplier,
    this.price,
    this.amount,
    this.soldAmount,
    this.rate,
    this.listedPrice,
    this.discount,
    this.startDateDiscount,
    this.endDateDiscount,
    this.saleable,
  );
}
