import 'package:sudoo/data/config/api_config.dart';

import '../api_service.dart';

class StorageService {
  static const storage = "/storage";
  static const images = "$storage/images";
  static const files = "$storage/files";
  static const uploadImageRoute = "$images/upload";
  static const uploadFileRoute = "$files/upload";

  final ApiService api;

  const StorageService(this.api);

  Future uploadImage(String path) => api.upload(
        uploadImageRoute,
        key: ApiConfig.image,
        filePath: path,
      );

  Future uploadImageBytes(List<int> bytes, {String? imageName}) => api.upload(
        uploadImageRoute,
        key: ApiConfig.image,
        fileName: imageName,
        bytes: bytes,
      );

  Future uploadFile(String path) => api.upload(
        uploadFileRoute,
        key: ApiConfig.file,
        filePath: path,
      );

  Future uploadFileBytes(List<int> bytes, {String? fileName}) => api.upload(
        uploadFileRoute,
        key: ApiConfig.file,
        fileName: fileName,
        bytes: bytes,
      );
}