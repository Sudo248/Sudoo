import 'package:flutter/material.dart';
import 'package:sudoo/app/base/base_page.dart';
import 'package:sudoo/app/pages/splash/splash_bloc.dart';
import 'package:sudoo/app/widgets/loading_view.dart';

import '../../routes/app_routes.dart';

class SplashPage extends BasePage<SplashBloc> {
  SplashPage({super.key});

  @override
  Widget build(BuildContext context) {
    bloc.setOnNavigationToDashBoard(() {
      Navigator.of(context).pushReplacementNamed(AppRoutes.dashboard);
    });
    bloc.setOnNavigationToAuth(() {
      Navigator.of(context).pushReplacementNamed(AppRoutes.auth);
    });
    bloc.refreshToken();
    return Center(
      child: LoadingView(
        controller: bloc.loadingController,
      ),
    );
  }
}
