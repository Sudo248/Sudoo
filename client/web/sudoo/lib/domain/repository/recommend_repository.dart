import 'package:sudoo/domain/model/model/model.dart';

import '../core/data_state.dart';

abstract class RecommendRepository {
  Future<DataState<List<Model>, Exception>> getAllModels();
}