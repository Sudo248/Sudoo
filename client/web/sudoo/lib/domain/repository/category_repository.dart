import 'package:sudoo/domain/model/discovery/category.dart';

import '../core/data_state.dart';

abstract class CategoryRepository {
  Future<DataState<List<Category>, Exception>> getCategories({bool includeCountProduct = false});
  // ADMIN
  Future<DataState<Category, Exception>> upsertCategory(Category category);
  Future<DataState<String, Exception>> deleteCategory(String categoryId);
}