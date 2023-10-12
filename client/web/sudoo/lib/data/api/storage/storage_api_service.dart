import '../api_service.dart';

class StorageService {
  static const storage = "/storage";
  static const images = "$storage/images";
  static const uploadImageRoute = "$images/upload";

  final ApiService api;

  const StorageService(this.api);

  Future uploadImage(String path) => api.upload(uploadImageRoute, filePath: path);

  Future uploadImageBytes(List<int> bytes) => api.upload(uploadImageRoute, bytes: bytes);
}