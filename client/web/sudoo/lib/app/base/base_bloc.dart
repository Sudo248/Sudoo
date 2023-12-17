import 'package:sudoo/app/base/base_mixin.dart';

abstract class BaseBloc with BaseMixin {
  void onInit();
  void onDispose();
}
