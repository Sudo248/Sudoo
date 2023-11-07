import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/splash/splash_bloc.dart';
import 'package:sudoo/app/widgets/loading_view.dart';

import '../../routes/app_routes.dart';

class SplashPage extends BasePage<SplashBloc> {
  SplashPage({super.key});

  @override
  Widget build(BuildContext context) {
    bloc.setOnNavigationToDashBoard((path) {
      context.go(path);
    });
    bloc.setOnNavigationToAuth(() {
      context.go(AppRoutes.auth);
    });
    bloc.refreshToken();
    return Center(
      child: LoadingView(
        controller: bloc.loadingController,
      ),
    );
  }
}
