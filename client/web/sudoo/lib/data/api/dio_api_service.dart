import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/data/api/api_service.dart';
import 'package:sudoo/domain/exceptions/api_exception.dart';
import 'package:sudoo/extensions/scope_function.dart';

import '../../utils/logger.dart';
import '../config/api_config.dart';
import '../config/pref_keys.dart';

class DioApiService implements ApiService {
  final Dio dio;
  final SharedPreferences pref;

  DioApiService({
    required this.dio,
    required this.pref,
    bool logging = false,
  }) {
    dio.interceptors.addAll(_interceptors(logging));
  }

  List<Interceptor> _interceptors(bool logging) {
    final interceptors = [_authenticationInterceptor()];
    if (logging) {
      interceptors.add(_loggingInterceptor());
    }
    return interceptors;
  }

  Interceptor _authenticationInterceptor() {
    return InterceptorsWrapper(
      onRequest: (request, handler) {
        final token = pref.getString(PrefKeys.token);
        request.headers
            .addAll({ApiConfig.tokenName: "${ApiConfig.tokenType} $token"});
        return handler.next(request);
      },
      onResponse: (response, handler) {
        return handler.next(response);
      },
      onError: (error, handler) {
        return handler.next(error);
      },
    );
  }

  Interceptor _loggingInterceptor() {
    return LogInterceptor(
      requestBody: true,
      responseBody: true,
    );
  }

  @override
  Future<Response> get(
    String path, {
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      final response = await dio.get(path, queryParameters: queryParameters);
      return response;
    } on Exception catch (e) {
      throw _handleException(e).also((it) {
        Logger.error(message: it.message);
      });
    }
  }

  @override
  Future<Response> post(
    String path, {
    dynamic body,
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      final response = await dio.post(
        path,
        data: body,
        queryParameters: queryParameters,
      );
      return response;
    } on Exception catch (e) {
      throw _handleException(e).also((it) {
        Logger.error(message: it.message);
      });
    }
  }

  Future<Response> put(
    String path, {
    dynamic body,
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      final response = await dio.put(
        path,
        data: body,
        queryParameters: queryParameters,
      );
      return response;
    } on Exception catch (e) {
      throw _handleException(e).also((it) {
        Logger.error(message: it.message);
      });
    }
  }

  @override
  Future<Response> delete(
    String path, {
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      final response = await dio.delete(
        path,
        queryParameters: queryParameters,
      );
      return response;
    } on Exception catch (e) {
      throw _handleException(e).also((it) {
        Logger.error(message: it.message);
      });
    }
  }

  @override
  Future<Response> upload(
    String path, {
    required String filePath,
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      MultipartFile multipartFile = await MultipartFile.fromFile(filePath);
      final formData = FormData.fromMap({
        ApiConfig.image: multipartFile,
      });
      final response = await dio.post(
        path,
        data: formData,
        options: Options(
          contentType: Headers.multipartFormDataContentType,
        ),
      );
      return response;
    } on Exception catch (e) {
      throw _handleException(e).also((it) {
        Logger.error(message: it.message);
      });
    }
  }

  ApiException _handleException(Exception exception) {
    if (exception is DioException) {
      switch (exception.type) {
        case DioExceptionType.cancel:
          return const RequestCancelled();
        case DioExceptionType.connectionTimeout:
          return const RequestTimeout();
        case DioExceptionType.receiveTimeout:
          return const ReceiveTimeout();
        case DioExceptionType.badResponse:
          final statusCode = exception.response?.statusCode ?? 0;
          return ApiException(statusCode, "Bad Resonse");
        default:
          final statusCode = exception.response?.statusCode ?? 0;
          return ApiException(statusCode, "Unexpected error");
      }
    }
    return const ServiceUnavailable();
  }
}
