import 'package:sudoo/app/pages/banner/banner_bloc.dart';
import 'package:sudoo/app/pages/category/category_bloc.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_bloc.dart';
import 'package:sudoo/app/pages/product/product/product_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/pages/promotion/promotion_bloc.dart';
import 'package:sudoo/app/pages/recommend/model_bloc.dart';
import 'package:sudoo/app/pages/splash/splash_bloc.dart';
import 'package:sudoo/app/pages/statstic/revenue/revenue_bloc.dart';
import 'package:sudoo/app/pages/stores/store_bloc.dart';
import 'package:sudoo/app/pages/supplier/supplier_bloc.dart';
import 'package:sudoo/app/pages/user/user_bloc.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';

import '../../utils/di.dart';
import '../pages/auth/auth_bloc.dart';
import '../pages/order/order/order_bloc.dart';
import '../pages/statstic/history_transaction/history_transaction_bloc.dart';

class AppModule {
  static Future<void> perform() async {

    getIt.registerLazySingleton<ScaffoldMessengerService>(
        () => ScaffoldMessengerService());

    getIt.registerFactory(() => SplashBloc(getIt.get(), getIt.get(), getIt.get(), getIt.get()));

    getIt.registerFactory(() => AuthBloc(getIt.get(), getIt.get()));

    getIt.registerLazySingleton(() => DashboardBloc(getIt.get()));

    getIt.registerLazySingleton(() => ProductListBloc(getIt.get(), getIt.get()));

    getIt.registerLazySingleton(() => OrderListBloc(getIt.get()));

    getIt.registerFactory(() => ProductBloc(getIt.get(), getIt.get(), getIt.get()));

    getIt.registerFactory(() => CategoryBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => PromotionBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => SupplierBloc(getIt.get(), getIt.get(), getIt.get()));

    getIt.registerFactory(() => UserBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => BannerBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => StoresBloc(getIt.get()));

    getIt.registerFactory(() => OrderBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => StatisticRevenueBloc(getIt.get(), getIt.get()));

    getIt.registerFactory(() => HistoryTransactionBloc(getIt.get()));

    getIt.registerFactory(() => ModelBloc(getIt.get(), getIt.get(), getIt.get()));

  }
}
