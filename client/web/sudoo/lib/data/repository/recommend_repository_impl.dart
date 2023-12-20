import 'package:sudoo/data/api/handle_response.dart';
import 'package:sudoo/data/api/recommend/recommend_api_service.dart';
import 'package:sudoo/domain/core/data_state.dart';
import 'package:sudoo/domain/model/model/model.dart';
import 'package:sudoo/domain/repository/recommend_repository.dart';

class RecommendRepositoryImpl with HandleResponse implements RecommendRepository {
  final RecommendService recommendService;

  RecommendRepositoryImpl(this.recommendService);

  @override
  Future<DataState<List<Model>, Exception>> getAllModels() async {
    final response = await handleResponse(
            () => recommendService.getAllModelVersions(),
        fromJson: (json) => (json as List<dynamic>).map((e) => Model.fromJson(e as Map<String, dynamic>)).toList());
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }
}