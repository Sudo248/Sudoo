import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/discovery/category.dart';
import 'package:sudoo/domain/repository/category_repository.dart';

import '../api/discovery/discovery_api_service.dart';
import '../dto/discovery/category_dto.dart';

class CategoryRepositoryImpl with HandleResponse implements CategoryRepository {

  final DiscoveryService discoveryService;

  const CategoryRepositoryImpl(this.discoveryService);

  @override
  Future<DataState<List<Category>, Exception>> getCategories() async {
    final response = await handleResponse(
            () => discoveryService.getCategories(),
        fromJson: (json) =>
            (json as List<dynamic>).map((e) =>
                CategoryDto.fromJson(e as Map<String, dynamic>),
            ));

    if (response.isSuccess) {
      print("Sudoo: ${response.get()}");
      final categories = response.get() as List<CategoryDto>;
      return DataState.success(categories.map((e) => e.toCategory()).toList());
    } else {
      return DataState.error(response.getError());
    }
  }

}