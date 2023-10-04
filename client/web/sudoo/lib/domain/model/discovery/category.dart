import 'package:sudoo/domain/model/discovery/product.dart';

class Category {
  final String categoryId;
  final String name;
  final String image;
  final List<Product>? products;

  const Category(
    this.categoryId,
    this.name,
    this.image, {
    this.products,
  });
}
