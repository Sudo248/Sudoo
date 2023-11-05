

class Category {
  final String categoryId;
  String name;
  String image;
  final int? countProduct;

  Category(
    this.categoryId,
    this.name,
    this.image, {
    this.countProduct,
  });

  factory Category.empty() {
    return Category("", "", "");
  }
}
