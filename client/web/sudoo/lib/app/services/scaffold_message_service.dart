import 'package:flutter/material.dart';

class ScaffoldMessengerService {
  final GlobalKey<ScaffoldMessengerState> _scaffoldKey = GlobalKey();

  GlobalKey<ScaffoldMessengerState> get scaffoldKey => _scaffoldKey;

  ScaffoldMessengerState get currentState => _scaffoldKey.currentState!;

  ScaffoldFeatureController<SnackBar, SnackBarClosedReason> showSnackBar(
    SnackBar snackBar,
  ) {
    return currentState.showSnackBar(snackBar);
  }

  void hideCurrentSnackBar() {
    currentState.hideCurrentSnackBar();
  }

  ScaffoldFeatureController<SnackBar, SnackBarClosedReason> showAppSnackBar({
    required Widget content,
    SnackBarAction? action,
    EdgeInsetsGeometry? padding,
    EdgeInsetsGeometry? margin,
    Color backgroundColor = Colors.white,
  }) {
    final size = MediaQuery.sizeOf(_scaffoldKey.currentContext!);
    final snackBar = SnackBar(
      content: content,
      action: action,
      backgroundColor: Colors.white,
      width: size.width * 0.2,
      behavior: SnackBarBehavior.floating,
      padding: padding,
      dismissDirection: DismissDirection.up,
    );
    return showSnackBar(snackBar);
  }

  ScaffoldFeatureController<SnackBar, SnackBarClosedReason> showErrorMessage(
    String message, {
    EdgeInsetsGeometry? padding,
    EdgeInsetsGeometry? margin,
    Color backgroundColor = Colors.white,
  }) {
    return showAppSnackBar(
      content: Text(
        message,
        style: const TextStyle(
          fontSize: 18,
          color: Colors.red,
        ),
      ),
    );
  }

  ScaffoldFeatureController<SnackBar, SnackBarClosedReason> showInfoMessage(
      String message, {
        EdgeInsetsGeometry? padding,
        EdgeInsetsGeometry? margin,
        Color backgroundColor = Colors.white,
      }) {
    return showAppSnackBar(
      content: Text(
        message,
        style: const TextStyle(
          fontSize: 18,
          color: Colors.black,
        ),
      ),
    );
  }
}
