abstract class ProductInfoActionCallback {
  Future<void> viewDetailProduct(String productId);
  void manageImages();
  Future<void> deleteProduct(String productId);
}