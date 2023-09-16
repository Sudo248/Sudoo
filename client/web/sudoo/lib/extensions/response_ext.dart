import 'dart:io';

import 'package:dio/dio.dart';

extension ResponseExt on Response {
  bool isSuccess() {
    return statusCode != null &&
        statusCode! >= HttpStatus.ok &&
        statusCode! <= HttpStatus.imUsed;
  }
}
