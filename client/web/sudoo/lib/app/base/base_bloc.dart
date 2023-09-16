import 'package:sudoo/app/base/base_mixin.dart';

import '../../utils/di.dart';
import '../routes/navigation.dart';
import '../services/navigator_service.dart';

abstract class BaseBloc with BaseMixin {
  void onInit();
  void onDispose();
}
