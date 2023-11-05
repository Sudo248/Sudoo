import 'package:flutter/foundation.dart';
import 'package:sudoo/app/base/base_bloc.dart';
import 'package:sudoo/domain/repository/auth_repository.dart';

import '../../../domain/model/auth/role.dart';
import '../../routes/app_router.dart';

class SplashBloc extends BaseBloc {
  final AuthRepository authRepository;
  late VoidCallback _navigateToDashboard, _navigateToAuth;

  void setOnNavigationToDashBoard(VoidCallback navigation) {
    _navigateToDashboard = navigation;
  }

  void setOnNavigationToAuth(VoidCallback navigation) {
    _navigateToAuth = navigation;
  }

  SplashBloc(this.authRepository);

  @override
  void onDispose() {
    loadingController.dispose();
  }

  @override
  void onInit() {
  }

  Future<void> refreshToken() async {
    loadingController.showLoading();
    await Future.delayed(const Duration(seconds: 2));
    try {
      final result = await authRepository.refreshToken();
      if (result.isSuccess) {
        AppRouter.isAdmin.value = result.get().role == Role.ADMIN;
        _navigateToDashboard();
      } else {
        _navigateToAuth();
      }
    } on Exception catch(_) {
      _navigateToAuth();
    }
    loadingController.hideLoading();
  }

}