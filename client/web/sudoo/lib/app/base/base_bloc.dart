import 'package:sudoo/app/base/base_mixin.dart';
import 'package:sudoo/app/widgets/loading_view.dart';

abstract class BaseBloc with BaseMixin {
  final LoadingViewController loadingController = LoadingViewController();
  void onInit();
  void onDispose();
}
