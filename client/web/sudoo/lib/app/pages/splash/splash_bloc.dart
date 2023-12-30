import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/data/config/pref_keys.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/domain/repository/order_repository.dart';
import 'package:sudoo/domain/repository/product_repository.dart';

import '../../../domain/model/auth/role.dart';
import '../../routes/app_router.dart';
import '../../routes/app_routes.dart';
import '../supplier/supplier_bloc.dart';

class SplashBloc extends BaseBloc {
  final AuthRepository authRepository;
  final ProductRepository productRepository;
  final OrderRepository orderRepository;
  late Function(String) _navigateToDashboard;
  late VoidCallback _navigateToAuth;
  final SharedPreferences pref;

  late Future taskGetConfig;

  void setOnNavigationToDashBoard(Function(String) navigation) {
    _navigateToDashboard = navigation;
  }

  void setOnNavigationToAuth(VoidCallback navigation) {
    _navigateToAuth = navigation;
  }

  SplashBloc(this.authRepository, this.productRepository, this.orderRepository, this.pref);

  @override
  void onDispose() {
    loadingController.dispose();
  }

  @override
  void onInit() {
    int? counter = pref.getInt(PrefKeys.counter);
    if (counter == null || counter % 2 == 0) {
      counter = 0;
    } else {
      counter += 1;
    }
    pref.setInt(PrefKeys.counter, counter);
    getConfig();
  }

  Future<void> refreshToken() async {
    loadingController.showLoading();
    final startTime = DateTime.now().millisecond;
    try {
      final result = await authRepository.refreshToken();
      if (result.isSuccess) {
        AppRouter.isAdmin = result.get().role == Role.ADMIN;
        // only check for staff
        if (!AppRouter.isAdmin) {
          final resultSupplier = await productRepository.getSupplier();
          if (!resultSupplier.isSuccess) {
            SupplierBloc.isRegistered = false;
            await taskGetConfig;
            await delayFrom(startTime: startTime);
            loadingController.hideLoading();
            _navigateToDashboard.call(AppRoutes.supplier);
            return;
          }
        }
        await taskGetConfig;
        await delayFrom(startTime: startTime);
        loadingController.hideLoading();
        _navigateToDashboard.call(AppRoutes.home);
      } else {
        await taskGetConfig;
        await delayFrom(startTime: startTime);
        loadingController.hideLoading();
        _navigateToAuth();
      }
    } on Exception catch(_) {
      await taskGetConfig;
      loadingController.hideLoading();
      _navigateToAuth();
    }
  }

  Future<void> delayFrom({required int startTime, int delay = 2}) {
    final currentTime = DateTime.now().millisecond;
    if (currentTime - startTime >= delay) return Future.value();
    return Future.delayed(Duration(milliseconds: delay - (currentTime - startTime)));
  }

  void getConfig() {
    taskGetConfig = Future.wait([
      authRepository.getConfig(),
      orderRepository.getConfig()
    ]);
  }
}