import 'package:flutter/foundation.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';
import 'package:sudoo/domain/repository/product_repository.dart';

import '../../../domain/model/auth/role.dart';
import '../../routes/app_router.dart';
import '../../routes/app_routes.dart';
import '../supplier/supplier_bloc.dart';

class SplashBloc extends BaseBloc {
  final AuthRepository authRepository;
  final ProductRepository productRepository;
  late Function(String) _navigateToDashboard;
  late VoidCallback _navigateToAuth;

  void setOnNavigationToDashBoard(Function(String) navigation) {
    _navigateToDashboard = navigation;
  }

  void setOnNavigationToAuth(VoidCallback navigation) {
    _navigateToAuth = navigation;
  }

  SplashBloc(this.authRepository, this.productRepository);

  @override
  void onDispose() {
    loadingController.dispose();
  }

  @override
  void onInit() {
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
            await deplayFrom(startTime: startTime);
            loadingController.hideLoading();
            _navigateToDashboard.call(AppRoutes.supplier);
            return;
          }
        }
        await deplayFrom(startTime: startTime);
        loadingController.hideLoading();
        _navigateToDashboard.call(AppRoutes.home);
      } else {
        await deplayFrom(startTime: startTime);
        loadingController.hideLoading();
        _navigateToAuth();
      }
    } on Exception catch(_) {
      loadingController.hideLoading();
      _navigateToAuth();
    }
  }

  Future<void> deplayFrom({required int startTime, int delay = 2}) {
    final currentTime = DateTime.now().millisecond;
    if (currentTime - startTime >= delay) return Future.value();
    return Future.delayed(Duration(milliseconds: delay - (currentTime - startTime)));
  }
}