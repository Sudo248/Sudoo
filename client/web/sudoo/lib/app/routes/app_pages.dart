import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_route.dart';
import 'package:sudoo/app/pages/auth/views/auth_page.dart';
import 'package:sudoo/app/pages/dashboard/views/dashboard_page.dart';
import 'package:sudoo/app/pages/home/home_page.dart';
import 'package:sudoo/app/pages/product/product/product_page.dart';
import 'package:sudoo/app/pages/product/product_list/product_list_page.dart';
import 'package:sudoo/app/pages/splash/splash_page.dart';
import 'package:sudoo/app/routes/app_routes.dart';

abstract class AppPages {

  static Route<dynamic> getPages(RouteSettings route) {
    switch (route.name) {
      case AppRoutes.home:
        return BaseRoute(builder: (ctx) => HomePage());
      case AppRoutes.productList:
        return BaseRoute(builder: (ctx) => ProductListPage());
      case AppRoutes.product:
        final String? productId = route.arguments as String?;
        return BaseRoute(builder: (ctx) => ProductPage(productId: productId));
      default:
        return BaseRoute(builder: (ctx) => const SizedBox.shrink());
    }
  }

  static Map<String, WidgetBuilder> getAppPageBuilder() {
    return {
      AppRoutes.splash: (context) => SplashPage(),
      AppRoutes.auth: (context) => AuthPage(),
      AppRoutes.dashboard: (context) => DashboardPage(),
    };
  }
}
