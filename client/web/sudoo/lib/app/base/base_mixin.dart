import 'package:sudoo/app/services/scaffold_message_service.dart';
import 'package:sudoo/utils/di.dart';

import '../services/navigator_service.dart';

mixin BaseMixin {
  final navigator = getIt.get<NavigatorService>();
  final scaffoldMessenger = getIt.get<ScaffoldMessengerService>();
}
