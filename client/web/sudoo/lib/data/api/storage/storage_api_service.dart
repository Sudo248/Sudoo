import '../api_service.dart';

class StorageService {
  static const storage = "/storage";
  static const images = "$storage/images";
  static const files = "$storage/files";
  static const uploadImageRoute = "$images/upload";
  static const uploadFileRoute = "$files/upload";

  final ApiService api;

  const StorageService(this.api);

  Future uploadImage(String path) => api.upload(uploadImageRoute, filePath: path);

  Future uploadImageBytes(List<int> bytes) => api.upload(uploadImageRoute, bytes: bytes);

  Future uploadFile(String path) => api.upload(uploadFileRoute, filePath: path);

  Future uploadFileBytes(List<int> bytes) => api.upload(uploadFileRoute, bytes: bytes);
}