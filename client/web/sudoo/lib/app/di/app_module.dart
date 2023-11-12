import 'package:sudoo/app/pages/banner/banner_bloc.dart';
import 'package:sudoo/app/pages/category/category_bloc.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/pages/home/home_bloc.dart';
import 'package:sudoo/app/pages/product/product/product_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/promotion/promotion_bloc.dart';
import 'package:sudoo/app/pages/splash/splash_bloc.dart';
import 'package:sudoo/app/pages/stores/store_bloc.dart';
import 'package:sudoo/app/pages/supplier/supplier_bloc.dart';
import 'package:sudoo/app/pages/user/user_bloc.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';

import '../../utils/di.dart';
import '../pages/auth/auth_bloc.dart';

class AppModule {
  static Future<void> perform() async {

    getIt.registerLazySingleton<ScaffoldMessengerService>(
        () => ScaffoldMessengerService());

    getIt.registerFactory(() => SplashBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => AuthBloc(getIt.get(), getIt.get()));

    getIt.registerLazySingleton(() => DashboardBloc(getIt.get()));

    getIt.registerLazySingleton(() => ProductListBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => ProductBloc(getIt.get(), getIt.get(), getIt.get()));

    getIt.registerFactory(() => HomeBloc(getIt.get()));

    getIt.registerFactory(() => CategoryBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => PromotionBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => SupplierBloc(getIt.get(), getIt.get(), getIt.get()));

    getIt.registerFactory(() => UserBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => BannerBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => StoresBloc(getIt.get()));

  }
}
