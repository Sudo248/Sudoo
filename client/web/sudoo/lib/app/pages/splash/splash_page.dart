import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_routes.dart';

class SplashPage extends StatelessWidget {
  const SplashPage({super.key});

  @override
  Widget build(BuildContext context) {
    Future.delayed(
      const Duration(seconds: 2),
      () {
        Navigator.of(context).pushReplacementNamed(AppRoutes.auth);
      },
    );
    return const SizedBox.expand(
      child: Center(
        child: FlutterLogo(),
      ),
    );
  }
}
