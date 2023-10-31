import 'package:sudoo/domain/core/data_state.dart';

abstract class StorageRepository {
  Future<DataState<String, Exception>> uploadImage(String pathImage);
  Future<DataState<String, Exception>> uploadImageBytes(List<int> bytes, {String? imageName});
  Future<DataState<String, Exception>> uploadFileBytes(List<int> bytes, {String? fileName});
}