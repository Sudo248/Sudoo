import 'package:sudoo/app/di/app_module.dart';
import 'package:sudoo/data/di/data_module.dart';

Future<void> setupServiceLocator() async {
  await DataModule.perform();
  await AppModule.perform();
}
