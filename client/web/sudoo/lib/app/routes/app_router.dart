import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/app/pages/banner/banner_page.dart';
import 'package:sudoo/app/pages/category/category_page.dart';
import 'package:sudoo/app/pages/dashboard/dashboard_page.dart';
import 'package:sudoo/app/pages/order/order/order_page.dart';
import 'package:sudoo/app/pages/order/order_list/order_list_page.dart';
import 'package:sudoo/app/pages/promotion/promotion_page.dart';
import 'package:sudoo/app/pages/recommend/model_page.dart';
import 'package:sudoo/app/pages/stores/stores_page.dart';
import 'package:sudoo/app/pages/supplier/supplier_page.dart';
import 'package:sudoo/app/pages/user/user_page.dart';
import 'package:sudoo/extensions/string_ext.dart';
import 'package:sudoo/utils/di.dart';

import '../../data/config/pref_keys.dart';
import '../../domain/model/auth/role.dart';
import '../pages/auth/views/auth_page.dart';
import '../pages/home/home_page.dart';
import '../pages/product/product/product_page.dart';
import '../pages/product/product_list/product_list_page.dart';
import '../pages/splash/splash_page.dart';
import '../pages/statstic/history_transaction/history_transaction_page.dart';
import '../pages/statstic/revenue/revenue_page.dart';
import 'app_routes.dart';

class AppRouter {
  static final GlobalKey<NavigatorState> _appNavigatorKey =
      GlobalKey<NavigatorState>(debugLabel: "app");
  static final GlobalKey<NavigatorState> _dashboardNavigatorKey =
      GlobalKey<NavigatorState>(debugLabel: "dashboard");

  static final GoRouter router = GoRouter(
    navigatorKey: _appNavigatorKey,
    initialLocation: AppRoutes.splash,
    routes: getAppRoutes(),
  );

  static final ValueNotifier<int> indexDashboard = ValueNotifier(0);
  static final ValueNotifier<int> adminIndexDashboard = ValueNotifier(0);
  static bool? _isAdmin;

  static bool isAdmin() {
    if (_isAdmin != null) {
      return _isAdmin!;
    } else {
      _isAdmin = getIt.get<SharedPreferences>().getString(PrefKeys.role) ==
          Role.ADMIN.value;
      return _isAdmin!;
    }
  }

  static void setAdmin(bool isAdmin) {
    _isAdmin = isAdmin;
  }

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
        pageBuilder: (context, state, child) {
          updateIndexDashboard(state.fullPath);
          return NoTransitionPage(
            key: state.pageKey,
            name: AppRoutes.dashboard,
            child: DashboardPage(
              child: child,
            ),
          );
        },
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
        builder: (context, state) => const HomePage(),
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
          name: AppRoutes.orders,
          path: AppRoutes.orders,
          builder: (context, state) => OrderListPage(),
          routes: [
            GoRoute(
              parentNavigatorKey: _dashboardNavigatorKey,
              name: AppRoutes.orderDetail,
              path: ":orderSupplierId",
              builder: (context, state) {
                final orderSupplierId = state.pathParameters['orderSupplierId'];
                return OrderPage(
                  orderSupplierId: orderSupplierId,
                );
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
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.statisticRevenue,
        path: AppRoutes.statisticRevenue,
        builder: (context, state) => StatisticRevenuePage(),
      ),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.historyTransaction,
        path: AppRoutes.historyTransaction,
        builder: (context, state) => HistoryTransactionPage(),
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
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.adminBanners,
        path: AppRoutes.adminBanners,
        builder: (context, state) => BannerPage(),
      ),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.adminStores,
        path: AppRoutes.adminStores,
        builder: (context, state) => StoresPage(),
      ),
      GoRoute(
        parentNavigatorKey: _dashboardNavigatorKey,
        name: AppRoutes.adminModel,
        path: AppRoutes.adminModel,
        builder: (context, state) => ModelPage(),
      ),
    ];
  }

  static updateIndexDashboard(String? routeName) {
    if (routeName.isNullOrEmpty) return;
    int index = 0;
    if (AppRouter.isAdmin()) {
      switch (routeName) {
        case AppRoutes.adminCategories:
          index = 1;
          break;
        case AppRoutes.adminPromotions:
          index = 2;
          break;
        case AppRoutes.adminBanners:
          index = 3;
          break;
        case AppRoutes.adminStores:
          index = 4;
          break;
        case AppRoutes.adminModel:
          index = 5;
        break;
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
        case AppRoutes.productDetail:
          index = 1;
          break;
        case AppRoutes.createProduct:
          index = 2;
          break;
        case AppRoutes.orders:
          index = 3;
          break;
        case AppRoutes.orderDetail:
          index = 3;
          break;
        case AppRoutes.statisticRevenue:
          index = 4;
          break;
        case AppRoutes.historyTransaction:
          index = 4;
          break;
        case AppRoutes.supplier:
          index = 5;
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
}

class DashboardGoRouterObserver extends NavigatorObserver {
  @override
  void didPop(Route route, Route? previousRoute) {
    final routeName = route.settings.name;
    if (routeName.isNullOrEmpty) return;
    int index = 0;
    if (AppRouter.isAdmin()) {
      switch (routeName) {
        case AppRoutes.adminCategories:
          index = 1;
          break;
        case AppRoutes.adminPromotions:
          index = 2;
          break;
        case AppRoutes.adminBanners:
          index = 3;
          break;
        case AppRoutes.adminStores:
          index = 4;
          break;
        case AppRoutes.adminModel:
          index = 5;
          break;
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
        case AppRoutes.productDetail:
          index = 1;
          break;
        case AppRoutes.createProduct:
          index = 2;
          break;
        case AppRoutes.orders:
          index = 3;
          break;
        case AppRoutes.orderDetail:
          index = 3;
          break;
        case AppRoutes.statisticRevenue:
          index = 4;
          break;
        case AppRoutes.historyTransaction:
          index = 4;
          break;
        case AppRoutes.supplier:
          index = 5;
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
}