import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/data/api/api_service.dart';
import 'package:sudoo/domain/exceptions/api_exception.dart';

import '../../utils/logger.dart';
import '../config/api_config.dart';
import '../config/pref_keys.dart';
import 'base_request.dart';

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
        request.headers.addAll({
          ApiConfig.tokenName: "${ApiConfig.tokenType} $token",
        });
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
    Map<String, dynamic>? headers,
  }) async {
    try {
      final options = Options(headers: headers);
      final response = await dio.get(
        path,
        queryParameters: queryParameters,
        options: options,
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  @override
  Future<Response> post(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  }) async {
    try {
      final options = Options(headers: headers);
      final response = await dio.post(
        path,
        data: body ?? request?.toJson(),
        queryParameters: queryParameters,
        options: options,
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  @override
  Future<Response> put(
    String path, {
    dynamic body,
    BaseRequest? request,
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  }) async {
    try {
      final options = Options(headers: headers);
      final response = await dio.put(
        path,
        data: body ?? request?.toJson(),
        queryParameters: queryParameters,
        options: options,
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  @override
  Future<Response> patch(String path,
      {body,
      BaseRequest? request,
      Map<String, dynamic>? queryParameters,
      Map<String, dynamic>? headers}) async {
    try {
      final options = Options(headers: headers);
      final response = await dio.patch(
        path,
        data: body ?? request?.toJson(),
        queryParameters: queryParameters,
        options: options,
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  @override
  Future<Response> delete(
    String path, {
    Map<String, dynamic>? queryParameters,
    Map<String, dynamic>? headers,
  }) async {
    try {
      final options = Options(headers: headers);
      final response = await dio.delete(
        path,
        queryParameters: queryParameters,
        options: options,
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  @override
  Future<Response> upload(
    String path, {
    String? filePath,
    List<int>? bytes,
    String? imageName,
    Map<String, dynamic>? queryParameters,
  }) async {
    try {
      late MultipartFile multipartFile;
      if (filePath != null) {
        multipartFile = await MultipartFile.fromFile(filePath);
      } else if (bytes != null) {
        multipartFile = MultipartFile.fromBytes(
          bytes,
          filename: "test.png"
        );
      } else {
        throw Exception("[filePath] or [bytes] must be not null");
      }
      final formData = FormData.fromMap(
        {
          ApiConfig.image: multipartFile,
        },
      );

      final response = await dio.post(
        path,
        data: formData,
        options: Options(
          contentType: Headers.multipartFormDataContentType,
        ),
      );
      return response;
    } on Exception catch (e) {
      Logger.error(message: e.toString());
      return _handleException(e);
    }
  }

  Response _handleException(Exception exception) {
    if (exception is DioException) {
      final response = exception.response;
      if (response != null) {
        return response;
      } else {
        throw const InternalServerError();
      }
    }
    throw const ServiceUnavailable();
  }
}
