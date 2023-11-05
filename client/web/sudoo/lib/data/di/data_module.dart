import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/data/api/auth/auth_api_service.dart';
import 'package:sudoo/data/api/dio_api_service.dart';
import 'package:sudoo/data/api/discovery/product_api_service.dart';
import 'package:sudoo/data/api/storage/storage_api_service.dart';
import 'package:sudoo/data/api/user/user_api_service.dart';
import 'package:sudoo/data/config/api_config.dart';
import 'package:sudoo/data/repository/auth_repository_impl.dart';
import 'package:sudoo/data/repository/product_repository_impl.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/domain/repository/category_repository.dart';
import 'package:sudoo/domain/repository/product_repository.dart';
import 'package:sudoo/domain/repository/storage_repository.dart';
import 'package:sudoo/domain/repository/user_repository.dart';
import 'package:sudoo/utils/di.dart';

import '../api/api_service.dart';
import '../repository/category_repository_impl.dart';
import '../repository/storage_repository_impl.dart';
import '../repository/user_repository_impl.dart';

class DataModule {
  static Future<void> perform() async {
    getIt.registerLazySingleton<Dio>(() {
      final baseOptions = BaseOptions(
        baseUrl: ApiConfig.baseUrl,
        connectTimeout: const Duration(milliseconds: ApiConfig.timeout),
        sendTimeout: const Duration(milliseconds: ApiConfig.timeout),
        receiveTimeout: const Duration(milliseconds: ApiConfig.timeout),
      );
      return Dio(baseOptions);
    });

    final pref = await SharedPreferences.getInstance();
    getIt.registerLazySingleton<SharedPreferences>(() => pref);

    getIt.registerLazySingleton<ApiService>(
      () => DioApiService(dio: getIt.get(), pref: getIt.get(), logging: true),
    );

    getIt.registerLazySingleton<AuthApiService>(
        () => AuthApiService(getIt.get()));

    getIt.registerLazySingleton<AuthRepository>(
      () => AuthRepositoryImpl(getIt.get(), pref: getIt.get()),
    );

    getIt.registerLazySingleton<ProductService>(
      () => ProductService(getIt.get()),
    );

    getIt.registerLazySingleton<ProductRepository>(
      () => ProductRepositoryImpl(getIt.get()),
    );

    getIt.registerLazySingleton<CategoryRepository>(
          () => CategoryRepositoryImpl(getIt.get()),
    );

    getIt.registerLazySingleton<StorageService>(
          () => StorageService(getIt.get())
    );

    getIt.registerLazySingleton<StorageRepository>(
          () => StorageRepositoryImpl(getIt.get()),
    );

    getIt.registerLazySingleton<UserService>(
          () => UserService(getIt.get()),
    );

    getIt.registerLazySingleton<UserRepository>(
          () => UserRepositoryImpl(getIt.get()),
    );
  }
}
