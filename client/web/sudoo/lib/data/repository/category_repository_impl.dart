import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/repository/category_repository.dart';

import '../api/discovery/product_api_service.dart';
import '../dto/discovery/category_dto.dart';

class CategoryRepositoryImpl with HandleResponse implements CategoryRepository {

  final ProductService productService;

  const CategoryRepositoryImpl(this.productService);

  @override
  Future<DataState<List<Category>, Exception>> getCategories({bool includeCountProduct = false}) async {
    final response = await handleResponse(
            () => productService.getCategories(includeCountProduct: includeCountProduct),
        fromJson: (json) =>
            (json as List<dynamic>).map((e) =>
                CategoryDto.fromJson(e as Map<String, dynamic>),
            ).toList());

    if (response.isSuccess) {
      final categories = response.get() as List<CategoryDto>;
      return DataState.success(categories.map((e) => e.toCategory()).toList());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<String, Exception>> deleteCategory(String categoryId) async {
    final response = await handleResponse(
          () => productService.deleteCategory(categoryId),
      fromJson: (json) => json,
    );
    if (response.isSuccess) {
      return DataState.success(categoryId);
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<Category, Exception>> upsertCategory(
      Category category,
      ) async {
    final response = await handleResponse(
      () => productService.upsertCategory(category.toCategoryDto()),
      fromJson: (json) => CategoryDto.fromJson(json as Map<String, dynamic>),
    );
    if (response.isSuccess) {
      final categoryDto = response.get() as CategoryDto;
      return DataState.success(categoryDto.toCategory());
    } else {
      return DataState.error(response.getError());
    }
  }
}