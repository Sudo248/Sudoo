import 'package:sudoo/data/api/discovery/request/upsert_file_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_product_request.dart';
import 'package:sudoo/data/dto/discovery/category_dto.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

import '../api_service.dart';

class ProductService {
  static const discovery = "/discovery";
  static const products = "$discovery/products";
  static const categories = "$discovery/categories";
  static const images = "$discovery/images";
  static const suppliers = "$discovery/suppliers";
  static const promotions = "$discovery/promotions";
  static const offsetKey = "offset";
  static const limitKey = "limit";
  static const selectKey = "select";

  final ApiService api;

  const ProductService(this.api);

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

  Future getCategories({bool includeCountProduct = false}) => api.get(categories, queryParameters: includeCountProduct ? {selectKey: "countProduct"} : null);

  Future upsertCategoryToProduct(String productId, String categoryId) =>
      api.post("$products/$productId/categories/$categoryId");

  Future deleteCategoryToProduct(String productId, String categoryId) =>
      api.delete("$products/$productId/categories/$categoryId");

  Future upsertImage(UpsertFileRequest request) => api.post(images, request: request);

  Future deleteImage(String imageId) => api.delete("$images/$imageId");

  Future upsertSupplier(Supplier supplier) => api.post(suppliers, body: supplier);

  Future getSupplier() => api.get("$suppliers/self");

  // ADMIN
  Future upsertCategory(CategoryDto categoryDto) => api.post(categories, body: categoryDto);

  Future deleteCategory(String categoryId) => api.delete("$categories/$categoryId");

  Future getPromotions() => api.get(promotions);

  Future upsertPromotion(Promotion promotion) => api.post(promotions, body: promotion);

  Future deletePromotion(String promotionId) => api.delete("$promotions/$promotionId");


}
