import 'package:sudoo/app/services/scaffold_message_service.dart';
import 'package:sudoo/domain/exceptions/base_exception.dart';
import 'package:sudoo/utils/di.dart';

mixin BaseMixin {
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
