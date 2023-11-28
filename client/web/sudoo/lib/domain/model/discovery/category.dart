

class Category {
  final String categoryId;
  String name;
  String image;
  bool enable = true;
  final int? countProduct;

  Category(
    this.categoryId,
    this.name,
    this.image,
      this.enable, {
    this.countProduct,
  });

  factory Category.empty() {
    return Category("", "", "", true);
  }
}
