import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/file.dart';
import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';
import 'package:sudoo/domain/model/discovery/supplier.dart';
import 'package:sudoo/domain/model/discovery/upsert_file.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';

import '../core/data_state.dart';
import '../model/discovery/category.dart';
import '../model/discovery/product_pagination.dart';
import '../model/promotion/promotion.dart';

abstract class ProductRepository {
  Future<DataState<ProductPagination<ProductInfo>, Exception>> getProducts(
    int offset,
    int limit,
  );

  Future<DataState<Product, Exception>> getProductDetail(String identify);

  Future<DataState<ProductInfo, Exception>> getProductInfo(String productId);

  Future<DataState<UpsertProduct, Exception>> upsertProduct(
      UpsertProduct product);

  Future<DataState<dynamic, Exception>> patchProduct(
      UpsertProduct product);

  Future<DataState<String, Exception>> deleteProduct(String productId);

  Future<DataState<List<Category>, Exception>> getCategories(String productId);

  Future<DataState<CategoryProduct, Exception>> upsertCategoryToProduct(CategoryProduct categoryProduct);

  Future<DataState<CategoryProduct, Exception>> deleteCategoryOfProduct(CategoryProduct categoryProduct);

  Future<DataState<File, Exception>> upsertImage(UpsertFile upsertImage);

  Future<DataState<File, Exception>> deleteImage(String imageId);

  Future<DataState<Supplier, Exception>> getSupplier();

  Future<DataState<Supplier, Exception>> upsertSupplier(Supplier supplier);

// Admin
  Future<DataState<List<Promotion>, Exception>> getPromotions();
  Future<DataState<Promotion, Exception>> upsertPromotion(Promotion promotion);
  Future<DataState<String, Exception>> deletePromotion(String promotionId);
}
