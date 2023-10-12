class ProductInfo {
  final String productId;
  final String sku;
  final String name;
  double price;
  double listedPrice;
  final int amount;
  final double rate;
  int discount;
  DateTime? startDateDiscount;
  DateTime? endDateDiscount;
  bool saleable;
  final String brand;
  final List<String> images;

  ProductInfo(
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
