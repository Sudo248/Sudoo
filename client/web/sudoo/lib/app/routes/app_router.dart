import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/pages/category/category_page.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_page.dart';
import 'package:sudoo/app/pages/promotion/promotion_page.dart';
import 'package:sudoo/app/pages/supplier/supplier_page.dart';
import 'package:sudoo/app/pages/user/user_page.dart';

import '../pages/auth/views/auth_page.dart';
import '../pages/home/home_page.dart';
import '../pages/product/product/product_page.dart';
import '../pages/product/product_list/product_list_page.dart';
import '../pages/splash/splash_page.dart';
import 'app_routes.dart';

class AppRouter {
  static final GlobalKey<NavigatorState> _appNavigatorKey = GlobalKey<NavigatorState>(debugLabel: "app");
  static final GlobalKey<NavigatorState> _dashboardNavigatorKey = GlobalKey<NavigatorState>(debugLabel: "dashboard");

  static final GoRouter router = GoRouter(
    navigatorKey: _appNavigatorKey,
    initialLocation: AppRoutes.splash,
    routes: getAppRoutes(),
  );

  static final ValueNotifier<int> indexDashboard = ValueNotifier(0);
  static final ValueNotifier<int> adminIndexDashboard = ValueNotifier(0);
  static final isAdmin = ValueNotifier(false);

  static List<RouteBase> getAppRoutes() {
    return <RouteBase>[
      GoRoute(
        name: AppRoutes.splash,
        path: AppRoutes.splash,
        builder: (context, state) => SplashPage(),
      ),
      GoRoute(
        name: AppRoutes.auth,
        path: AppRoutes.auth,
        builder: (context, state) => AuthPage(),
      ),
      GoRoute(
        name: AppRoutes.user,
        path: AppRoutes.user,
        builder: (context, state) => UserPage(),
      ),
      ShellRoute(
        navigatorKey: _dashboardNavigatorKey,
        routes: getDashBoardRoutes(),
        pageBuilder: (context, state, child) => NoTransitionPage(
          key: state.pageKey,
          name: AppRoutes.dashboard,
          child: DashboardPage(
            child: child,
          ),
        ),
        observers: [DashboardGoRouterObserver()],
      )
    ];
  }

  static List<RouteBase> getDashBoardRoutes() {
    return <RouteBase>[
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.home,
        path: AppRoutes.home,
        builder: (context, state) => HomePage(),
      ),
      GoRoute(
          parentNavigatorKey: _dashboardNavigatorKey,
          name: AppRoutes.products,
          path: AppRoutes.products,
          builder: (context, state) => ProductListPage(),
          routes: [
            GoRoute(
              parentNavigatorKey: _dashboardNavigatorKey,
              name: AppRoutes.productDetail,
              path: ":productId",
              builder: (context, state) {
                final productId = state.pathParameters['productId'];
                return ProductPage(productId: productId);
              },
            ),
          ]),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.createProduct,
        path: AppRoutes.createProduct,
        builder: (context, state) => ProductPage(),
      ),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.supplier,
        path: AppRoutes.supplier,
        builder: (context, state) => SupplierPage(),
      ),
      // category
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.adminCategories,
        path: AppRoutes.adminCategories,
        builder: (context, state) => CategoryPage(),
      ),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.adminPromotions,
        path: AppRoutes.adminPromotions,
        builder: (context, state) => PromotionPage(),
      ),
    ];
  }
}

class DashboardGoRouterObserver extends NavigatorObserver {
  @override
  void didPush(Route route, Route? previousRoute) {
    final routeName = route.settings.name;
    int index = 0;
    if (AppRouter.isAdmin.value) {
      switch (routeName) {
        case AppRoutes.adminCategories:
          index = 1;
          break;
        case AppRoutes.createProduct:
          index = 2;
        default:
          index = 0;
          break;
      }
      if (index != AppRouter.adminIndexDashboard.value) {
        AppRouter.adminIndexDashboard.value = index;
      }
    } else {
      switch (routeName) {
        case AppRoutes.products:
          index = 1;
          break;
        case AppRoutes.createProduct:
          index = 2;
          break;
        case AppRoutes.adminCategories:
          index = 3;
          break;
        case AppRoutes.supplier:
          index = 4;
          break;
        default:
          index = 0;
          break;
      }
      if (index != AppRouter.indexDashboard.value) {
        AppRouter.indexDashboard.value = index;
      }
    }
  }

  @override
  void didPop(Route route, Route? previousRoute) {}

  @override
  void didRemove(Route route, Route? previousRoute) {}

  @override
  void didReplace({Route? newRoute, Route? oldRoute}) {}
}

class AppGoRouterObserver extends NavigatorObserver {


}
