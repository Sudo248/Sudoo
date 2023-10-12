import 'package:flutter/material.dart';
import 'package:sudoo/app/pages/auth/views/auth_page.dart';
import 'package:sudoo/app/pages/dashboard/views/dashboard_page.dart';
import 'package:sudoo/app/pages/home/home_page.dart';
import 'package:sudoo/app/pages/splash/splash_page.dart';
import 'package:sudoo/app/routes/app_pages.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/di.dart';

import 'app/base/setup_service_locator.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await setupServiceLocator();
  runApp(const SudooApp());
}

class SudooApp extends StatelessWidget {
  const SudooApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // navigatorKey: getIt.get<NavigatorService>(instanceName: Navigation.main.name).navigatorKey,
      debugShowCheckedModeBanner: false,
      title: R.string.appName,
      scaffoldMessengerKey: getIt.get<ScaffoldMessengerService>().scaffoldKey,
      theme: R.theme.appTheme,
      routes: AppPages.getAppPageBuilder(),
      home: AuthPage(),
    );
  }
}

