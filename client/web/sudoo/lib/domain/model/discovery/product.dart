import 'category.dart';
import 'supplier.dart';

class Product {
  final String productId;
  final String sku;
  final String name;
  final String description;
  final double price;
  final double listedPrice;
  final int amount;
  final int soldAmount;
  final double rate;
  final int discount;
  final DateTime? startDateDiscount;
  final DateTime? endDateDiscount;
  final bool saleable;
  final List<String> images;
  final Supplier supplier;
  final List<Category> categories;

  const Product(
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
