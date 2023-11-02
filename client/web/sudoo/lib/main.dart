import 'package:flutter/material.dart';
import 'package:sudoo/app/routes/app_router.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';
import 'package:sudoo/resources/R.dart';
import 'package:sudoo/utils/di.dart';
import 'package:url_strategy/url_strategy.dart';

import 'app/base/setup_service_locator.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await setupServiceLocator();
  setPathUrlStrategy();
  runApp(const SudooApp());
}

class SudooApp extends StatelessWidget {
  const SudooApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      debugShowCheckedModeBanner: false,
      title: R.string.appName,
      scaffoldMessengerKey: getIt.get<ScaffoldMessengerService>().scaffoldKey,
      theme: R.theme.appTheme,
      routerConfig: AppRouter.router,
    );
  }
}

