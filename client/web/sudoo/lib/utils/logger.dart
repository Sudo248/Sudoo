import 'package:flutter/foundation.dart';

class Logger {
  final String key, message;
  // error
  Logger.error({this.key = "ERROR", required this.message}) {
    if (!kReleaseMode) {
      // red
      // ignore: avoid_print
      print('\x1B[31m==> $key : $message\x1B[0m');
    }
  }
  // debug
  Logger.debug({this.key = "Debug", required this.message}) {
    if (!kReleaseMode) {
      // cyan
      // ignore: avoid_print
      print('\x1B[36m==> $key : $message\x1B[0m');
    }
  }
  // warning
  Logger.warning({this.key = "Warning", required this.message}) {
    if (!kReleaseMode) {
      // yellow
      // ignore: avoid_print
      print('\x1B[33m==> $key : $message\x1B[0m');
    }
  }

  // success
  Logger.success({this.key = "Success", required this.message}) {
    if (!kReleaseMode) {
      // green
      // ignore: avoid_print
      print('\x1B[32m==> $key : $message\x1B[0m');
    }
  }
}
