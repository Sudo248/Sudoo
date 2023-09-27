import 'package:flutter/material.dart';
import 'package:sudoo/app/services/scaffold_message_service.dart';
import 'package:sudoo/domain/exceptions/base_exception.dart';
import 'package:sudoo/utils/di.dart';

import '../services/navigator_service.dart';

mixin BaseMixin {
  final navigator = getIt.get<NavigatorService>();
  final scaffoldMessenger = getIt.get<ScaffoldMessengerService>();

  void showMessage(String message) {
    scaffoldMessenger.showErrorMessage(message);
  }

  void showErrorMessage(Exception exception) {
    var message = "";
    if (exception is BaseException) {
      message = exception.message.toString();
    } else {
      message = exception.toString();
    }
    scaffoldMessenger.showErrorMessage(message);
  }

  void hideMessage() {
    scaffoldMessenger.hideCurrentSnackBar();
  }
}
