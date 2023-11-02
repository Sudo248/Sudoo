import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/pages/dashboard/views/dashboard_page.dart';

import '../pages/auth/views/auth_page.dart';
import '../pages/home/home_page.dart';
import '../pages/product/product/product_page.dart';
import '../pages/product/product_list/product_list_page.dart';
import '../pages/splash/splash_page.dart';
import 'app_routes.dart';

class AppRouter {
  static final GlobalKey<NavigatorState> _appNavigatorKey =
      GlobalKey<NavigatorState>(debugLabel: "app");
  static final GlobalKey<NavigatorState> _dashboardNavigatorKey =
      GlobalKey<NavigatorState>(debugLabel: "dashboard");

  static final GoRouter router = GoRouter(
    navigatorKey: _appNavigatorKey,
    initialLocation: AppRoutes.auth,
    routes: getAppRoutes(),
  );

  static final indexDashboard = ValueNotifier(0);

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
        observers: [
          DashboardGoRouterObserver()
        ]
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
      )
    ];
  }
}

class DashboardGoRouterObserver extends NavigatorObserver {
  @override
  void didPush(Route route, Route? previousRoute) {

  }

  @override
  void didPop(Route route, Route? previousRoute) {
  }

  @override
  void didRemove(Route route, Route? previousRoute) {
  }

  @override
  void didReplace({Route? newRoute, Route? oldRoute}) {
  }
}
