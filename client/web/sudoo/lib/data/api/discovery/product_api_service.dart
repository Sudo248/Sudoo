import 'package:sudoo/data/api/discovery/request/upsert_file_request.dart';
import 'package:sudoo/data/api/discovery/request/upsert_product_request.dart';
import 'package:sudoo/data/dto/discovery/category_dto.dart';
import 'package:sudoo/data/dto/discovery/file_dto.dart';
import 'package:sudoo/domain/model/discovery/order_by.dart';
import 'package:sudoo/domain/model/discovery/sort_by.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/model/discovery/transaction.dart';
import 'package:sudoo/domain/model/promotion/promotion.dart';

import '../api_service.dart';

class ProductService {
  static const discovery = "/discovery";
  static const products = "$discovery/products";
  static const categories = "$discovery/categories";
  static const images = "$discovery/images";
  static const suppliers = "$discovery/suppliers";
  static const banners = "$discovery/banners";
  static const promotions = "/promotions";
  static const offsetKey = "offset";
  static const limitKey = "limit";
  static const sortByKey = "sortBy";
  static const orderByKey = "orderBy";
  static const selectKey = "select";
  static const createdAt = "created_at";
  static const asc = "asc";
  static const desc = "desc";

  final ApiService api;

  const ProductService(this.api);

  Future getProducts(int offset, int limit) => api.get(
        products,
        queryParameters: {
          offsetKey: offset,
          limitKey: limit,
          sortByKey: createdAt,
          orderByKey: desc,
        },
      );

  Future getProductDetail(String identify) => api.get("$products/$identify");

  Future getProductInfo(String productId) => api.get("$products/$productId/info");

  Future upsertProduct(UpsertProductRequest request) =>
      api.post(products, request: request);

  Future patchProduct(UpsertProductRequest request) =>
      api.patch(products, request: request);

  Future deleteProduct(String productId) => api.delete("$products/$productId");

  Future getCategoriesByProductId(String productId) =>
      api.get("$products/$productId/categories");

  Future getCategories({bool includeCountProduct = false}) =>
      api.get(categories,
          queryParameters:
              includeCountProduct ? {selectKey: "countProduct"} : null);

  Future upsertCategoryToProduct(String productId, String categoryId) =>
      api.post("$products/$productId/categories/$categoryId");

  Future deleteCategoryToProduct(String productId, String categoryId) =>
      api.delete("$products/$productId/categories/$categoryId");

  Future upsertImage(UpsertFileRequest request) =>
      api.post(images, request: request);

  Future deleteImage(String imageId) => api.delete("$images/$imageId");

  Future upsertSupplier(Supplier supplier) =>
      api.post(suppliers, request: supplier);

  Future getSupplier() => api.get("$suppliers/self");

  Future getSuppliers() => api.get(suppliers);

  Future getSupplierProducts(
    int offset,
    int limit, {
    SortBy sortBy = SortBy.createdAt,
    OrderBy orderBy = OrderBy.desc,
  }) =>
      api.get(
        "$suppliers/self/products",
        queryParameters: {
          offsetKey: offset,
          limitKey: limit,
          sortByKey: sortBy.value,
          orderByKey: orderBy.value,
        },
      );

  Future getSupplierRevenue() => api.get("$suppliers/self/revenue");

  Future claimSupplierRevenue(Transaction transaction) => api.put("$suppliers/self/transactions", request: transaction);

  Future getHistoryTransaction() => api.get("$suppliers/self/transactions");

  // ADMIN
  Future upsertCategory(CategoryDto categoryDto) =>
      api.post(categories, request: categoryDto);

  Future deleteCategory(String categoryId) =>
      api.delete("$categories/$categoryId");

  Future getPromotions() => api.get(promotions);

  Future upsertPromotion(Promotion promotion) =>
      api.post(promotions, request: promotion);

  Future deletePromotion(String promotionId) =>
      api.delete("$promotions/$promotionId");
  
  Future getBanners() => api.get(banners);

  Future upsertBanner(FileDto banner) => api.post(banners, request: banner);
}
