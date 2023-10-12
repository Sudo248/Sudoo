import 'base_request.dart';

abstract class ApiService {
  Future get(
    String path, {
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  });

  Future post(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  });

  Future put(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  });

  Future patch(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  });

  Future delete(
    String path, {
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  });

  Future upload(
    String path, {
    String? filePath,
    List<int>? bytes,
    String? imageName,
    Map<String, dynamic>? queryParameters,
  });
}
