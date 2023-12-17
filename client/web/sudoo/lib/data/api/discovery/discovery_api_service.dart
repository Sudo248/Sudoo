import 'package:sudoo/data/api/discovery/request/upsert_product_request.dart';

import '../api_service.dart';

class DiscoveryService {
  static const discovery = "/discovery";
  static const products = "$discovery/products";
  static const categories = "$discovery/categories";
  static const offsetKey = "offset";
  static const limitKey = "limit";

  final ApiService api;

  const DiscoveryService(this.api);

  Future getProducts(int offset, int limit) => api.get(
        products,
        queryParameters: {
          offsetKey: offset,
          limitKey: limit,
        },
      );

  Future getProductDetail(String identify) => api.get("$products/$identify");

  Future upsertProduct(UpsertProductRequest request) =>
      api.post(products, request: request);

  Future patchProduct(UpsertProductRequest request) =>
      api.patch(products, request: request);

  Future deleteProduct(String productId) => api.delete("$products/$productId");

  Future getCategoriesByProductId(String productId) =>
      api.get("$products/$productId/categories");

  Future getCategories() => api.get(categories);

  Future upsertCategoryToProduct(String productId, String categoryId) =>
      api.post("$products/$productId/categories/$categories");

  Future deleteCategoryToProduct(String productId, String categoryId) =>
      api.delete("$products/$productId/categories/$categories");
}
