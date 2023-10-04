import 'package:sudoo/app/pages/dashboard/dashboard_bloc.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_bloc.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';

import '../../utils/di.dart';
import '../pages/auth/auth_bloc.dart';
import '../services/navigator_service.dart';

class AppModule {
  static Future<void> perform() async {
    // getIt.registerLazySingleton<NavigatorService>(() => NavigatorService(), instanceName: Navigation.main.name);
    getIt.registerLazySingleton<NavigatorService>(() => NavigatorService());

    getIt.registerLazySingleton<ScaffoldMessengerService>(
        () => ScaffoldMessengerService());

    getIt.registerFactory(() => AuthBloc(getIt.get()));

    getIt.registerFactory(() => DashboardBloc());

    getIt.registerFactory(() => ProductListBloc(getIt.get(), getIt.get()));
  }
}
