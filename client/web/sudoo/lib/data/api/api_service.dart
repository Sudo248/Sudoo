import 'base_request.dart';

abstract class ApiService {
  Future get(
    String path, {
    Map<String, dynamic>? queryParameters,
  });

  Future post(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
  });

  Future put(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
  });

  Future delete(
    String path, {
    Map<String, dynamic>? queryParameters,
  });

  Future upload(
    String path, {
    required String filePath,
    Map<String, dynamic>? queryParameters,
  });
}
