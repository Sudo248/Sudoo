import 'package:sudoo/data/api/handle_response.dart';

import '../../domain/core/data_state.dart';
import '../../domain/repository/storage_repository.dart';
import '../api/storage/storage_api_service.dart';

class StorageRepositoryImpl with HandleResponse implements StorageRepository {
  final StorageService storageService;

  StorageRepositoryImpl(this.storageService);

  @override
  Future<DataState<String, Exception>> uploadImage(String pathImage) async {
    final response = await handleResponse(
      () => storageService.uploadImage(pathImage),
      fromJson: (json) => (json as Map<String, dynamic>)["imageUrl"],
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<String, Exception>> uploadImageBytes(List<int> bytes, {String? imageName}) async {
    final response = await handleResponse(
          () => storageService.uploadImageBytes(bytes, imageName: imageName),
      fromJson: (json) => (json as Map<String, dynamic>)["imageUrl"],
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }

  @override
  Future<DataState<String, Exception>> uploadFileBytes(List<int> bytes, {String? fileName}) async {
    final response = await handleResponse(
          () => storageService.uploadFileBytes(bytes, fileName: fileName),
      fromJson: (json) => (json as Map<String, dynamic>)["fileUrl"],
    );
    if (response.isSuccess) {
      return DataState.success(response.get());
    } else {
      return DataState.error(response.getError());
    }
  }
}
