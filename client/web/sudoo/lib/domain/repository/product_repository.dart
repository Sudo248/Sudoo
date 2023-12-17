import 'package:sudoo/domain/model/discovery/category_product.dart';
import 'package:sudoo/domain/model/discovery/product.dart';
import 'package:sudoo/domain/model/discovery/product_info.dart';
import 'package:sudoo/domain/model/discovery/upsert_product.dart';

import '../core/data_state.dart';
import '../model/discovery/category.dart';
import '../model/discovery/product_pagination.dart';

abstract class ProductRepository {
  Future<DataState<ProductPagination<ProductInfo>, Exception>> getProducts(
    int offset,
    int limit,
  );

  Future<DataState<Product, Exception>> getProductDetail(String identify);

  Future<DataState<UpsertProduct, Exception>> upsertProduct(
      UpsertProduct product);

  Future<DataState<UpsertProduct, Exception>> patchProduct(
      UpsertProduct product);

  Future<DataState<String, Exception>> deleteProduct(String productId);

  Future<DataState<List<Category>, Exception>> getCategories(String productId);

  Future<DataState<CategoryProduct, Exception>> upsertCategoryToProduct(CategoryProduct categoryProduct);

  Future<DataState<CategoryProduct, Exception>> deleteCategoryToProduct(CategoryProduct categoryProduct);
}
