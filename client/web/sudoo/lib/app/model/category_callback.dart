import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/model/discovery/category_product.dart';

abstract class CategoryCallback {
  Future<List<Category>> getCategoriesOfProduct(String productId);

  Future<CategoryProduct> upsertCategoryToProduct(
    CategoryProduct categoryProduct,
  );

  Future<CategoryProduct> deleteCategoryToProduct(
    CategoryProduct categoryProduct,
  );

  List<Category> getCategoriesWithout(List<Category> categories);
}
